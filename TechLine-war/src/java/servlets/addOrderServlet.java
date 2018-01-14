/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import entities.OrderAddress;
import entities.OrderAddressFacadeLocal;
import entities.OrderDetails;
import entities.OrderDetailsFacadeLocal;
import entities.OrderDetailsPK;
import entities.OrderMaster;
import entities.OrderMasterFacadeLocal;
import entities.Products;
import entities.ProductsFacadeLocal;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.ProductInCart;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author nth15
 */
public class addOrderServlet extends HttpServlet {
    @EJB
    private OrderAddressFacadeLocal orderAddressFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;
    @EJB
    private OrderDetailsFacadeLocal orderDetailsFacade;
    @EJB
    private OrderMasterFacadeLocal orderMasterFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            String message = "";
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            if (user == null) {
                message = "Please log in";
            }
            List<ProductInCart> cart = (List<ProductInCart>) session.getAttribute("cart");
            ProductInCart pInCart = new ProductInCart();
            
            switch (action) {
                case "addToCart":
                    StringBuilder sb = new StringBuilder();
                    boolean isExisted = false;
                    String id = request.getParameter("idProduct");
                    Products currentP = productsFacade.find(id);
                    int quantityDemand;
                    String redirect = "HomeServlet";
                    if (StringUtils.isBlank(request.getParameter("quantity"))) {
                        quantityDemand = 1;
                    }
                    else {
                        quantityDemand = Integer.parseInt(request.getParameter("quantity"));
                    }
                    if (quantityDemand > currentP.getProductQuantity()) {
                        sb.append("We dont have that much products in store, We just can offer you as much as ");
                        sb.append(currentP.getProductQuantity());
                        sb.append(" ");
                        sb.append(currentP.getProductUnit());
                        message =  sb.toString();
                    }
                    else {
                        if (cart != null) {
                            for (ProductInCart p : cart ) {
                                if (p != null && StringUtils.equals(p.getProductId(), id)) {
                                    int quantity = p.getQuantity() + quantityDemand;
                                    double total = p.getPrice() * quantity;
                                    cart.remove(p);
                                    p.setQuantity(quantity);
                                    p.setTotal(total);
                                    cart.add(p);
                                    isExisted = true;
                                    break;
                                }
                            }
                        }
                        else {
                            cart = new ArrayList<>();
                        }
                        if (!isExisted) {
                            pInCart.setProductId(id);
                            pInCart.setQuantity(quantityDemand);
                            pInCart.setName(currentP.getProductName());
                            pInCart.setPrice(currentP.getProductPrice());
                            String image = currentP.getProductImage().split(",")[0];
                            pInCart.setImage(image);
                            double total = pInCart.getPrice() * pInCart.getQuantity();
                            pInCart.setTotal(total);
                            cart.add(pInCart);
                        }
                    }
                    
                    session.setAttribute("user", user);
                    session.setAttribute("cart", cart);
                    if ("search".equals(request.getParameter("fromjsp"))) {
                        redirect = "search.jsp";
                    }
                    if (StringUtils.isNotBlank(message)) {
                        request.setAttribute("message", message);
                    }
                    else {
                        request.setAttribute("swalMessage", "Add to Cart successfully");
                    }
                    request.getRequestDispatcher(redirect).forward(request, response);
                    break;
                    
                case "checkout":
                    if (user == null || cart == null) {
                        message = "Your session is ended. Please login and select product again.";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("HomeServlet").forward(request, response);
                        break;
                    }
                    String totalPrice = request.getParameter("txtTotalPrice");
                    String deliveryFee = request.getParameter("txtDeliveryPrice")+".0";
                    String orderNote = request.getParameter("deliveryRequest");
                    String phone = request.getParameter("txtPhone");
                    OrderMaster orderMaster = new OrderMaster();
                    String newOrderId = orderMasterFacade.newId();
                    Date date = new Date();
                    orderMaster.setOrderMId(newOrderId);
                    orderMaster.setUserId(user);
                    orderMaster.setOrderTotalPrice(Double.parseDouble(totalPrice));
                    orderMaster.setDeliveryPrice(Double.parseDouble(deliveryFee));
                    orderMaster.setOrderNote(orderNote);
                    orderMaster.setOrderStatus("Processing");
                    orderMaster.setDateOrdered(date);
                    orderMasterFacade.create(orderMaster);
                    for (ProductInCart p : cart) {
                        Products productAvailable = productsFacade.find(p.getProductId());
                        if (p.getQuantity() > productAvailable.getProductQuantity()) {
                            orderMasterFacade.remove(orderMaster);
                            cart.remove(p);
                            message = "We're sorry. Your selected product: "+ productAvailable.getProductName()+ " is out of stock. Please choose another";
                            request.setAttribute("message", message);
                            request.getRequestDispatcher("HomeServlet").forward(request, response);
                            break;
                        }
                        OrderDetails orderDetail = new OrderDetails();
                        OrderDetailsPK orderDetailPk = new OrderDetailsPK();
                        orderDetailPk.setOrderMId(newOrderId);
                        orderDetailPk.setProductId(p.getProductId());
                        orderDetail.setOrderDetailsPK(orderDetailPk);
                        orderDetail.setProducts(productAvailable);
                        orderDetail.setQuantity(p.getQuantity());
                        orderDetailsFacade.create(orderDetail);
                        int quantityLeft = productAvailable.getProductQuantity() - p.getQuantity();
                        productAvailable.setProductQuantity(quantityLeft);
                        productsFacade.edit(productAvailable);
                        orderMaster.getOrderDetailsCollection().add(orderDetail);
                    }
                    OrderAddress orderAddress = new OrderAddress();
                    orderAddress.setOrderMId(newOrderId);
                    orderAddress.setOrderMaster(orderMaster);
                    orderAddress.setOrderPhone(phone);
                    orderAddress.setOrderAddressLat(Double.MIN_VALUE);
                    orderAddress.setOrderAddressLng(Double.MIN_VALUE);
                    orderAddress.setUserId(user);
                    orderAddressFacade.create(orderAddress);
                    orderMaster.setOrderAddress(orderAddress);
                    orderMasterFacade.edit(orderMaster);
                    message = "Your order completed. Please wait for our delivery team call... Thank you";
                    session.setAttribute("cart", null);
                    session.removeAttribute("cart");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("HomeServlet").forward(request, response);
                    break;
                default:
                    request.setAttribute("error", "Page not found");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
