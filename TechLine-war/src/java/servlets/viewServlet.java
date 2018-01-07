/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.BrandsFacadeLocal;
import entities.Categories;
import entities.CategoriesFacadeLocal;
import entities.Customers;
import entities.CustomersFacadeLocal;
import entities.OrderDetailsFacadeLocal;
import entities.OrderMasterFacadeLocal;
import entities.ProductTypes;
import entities.ProductTypesFacadeLocal;
import entities.Products;
import entities.ProductsCommentFacadeLocal;
import entities.ProductsFacadeLocal;
import entities.Seller;
import entities.SellerFacadeLocal;
import entities.Users;
import entities.UsersFacadeLocal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.ProductListAdminModel;
import models.TopProductModel;
import utils.TechLineUtils;
import utils.PageProduct;
import models.SellerOrder;

/**
 *
 * @author nth15
 */
public class viewServlet extends HttpServlet {

    @EJB
    private OrderDetailsFacadeLocal orderDetailsFacade;

    @EJB
    private CustomersFacadeLocal customersFacade;
    @EJB
    private ProductsCommentFacadeLocal productsCommentFacade;

    @EJB
    private OrderMasterFacadeLocal orderMasterFacade;

    @EJB
    private UsersFacadeLocal usersFacade;
    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;
    @EJB
    private ProductTypesFacadeLocal productTypesFacade;
    @EJB
    private SellerFacadeLocal sellerFacadeLocal;
    @EJB
    private BrandsFacadeLocal brandsFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            Users user = (Users) request.getSession().getAttribute("user");
            switch (action) {
                case "cateDetail":
                    Categories categories = categoriesFacade.find(request.getParameter("idCate"));
                    List<Products> listProduct = new ArrayList<>();
                    List<ProductTypes> listProductTypes = (List<ProductTypes>) categories.getProductTypesCollection();
                    for (ProductTypes productTypes : listProductTypes) {
                        listProduct.addAll(productTypes.getProductsCollection());
                    }
                    PageProduct pageProduct = new PageProduct(TechLineUtils.buidProductIndexModel(listProduct), 12);
                    String n = request.getParameter("btn");
                    if (n != null) {
                        if (n.equals("next")) {
                            pageProduct.next();
                        }
                        if (n.equals("prev")) {
                            pageProduct.prev();
                        }
                    }
                    String pages = request.getParameter("page");
                    if (pages != null) {
                        int m = Integer.parseInt(pages);
                        pageProduct.setPageIndex(m);
                        pageProduct.updateModel();
                    }
                    request.setAttribute("pageProduct", pageProduct);
                    request.setAttribute("listProduct", listProduct);
                    request.setAttribute("category", categories);
                    request.setAttribute("listCategories", categoriesFacade.findAll());
                    request.getRequestDispatcher("categoryDetail.jsp").forward(request, response);
                    break;
                case "typeDetail":
                    ProductTypes productTypes = productTypesFacade.find(request.getParameter("idType"));
                    List<Products> listProduct2 = new ArrayList<>();
                    listProduct2.addAll(productTypes.getProductsCollection());
                    PageProduct pageProduct2 = new PageProduct(TechLineUtils.buidProductIndexModel(listProduct2), 12);
                    String n1 = request.getParameter("btn");
                    if (n1 != null) {
                        if (n1.equals("next")) {
                            pageProduct2.next();
                        }
                        if (n1.equals("prev")) {
                            pageProduct2.prev();
                        }
                    }
                    String pages1 = request.getParameter("page");
                    if (pages1 != null) {
                        int m = Integer.parseInt(pages1);
                        pageProduct2.setPageIndex(m);
                        pageProduct2.updateModel();
                    }
                    request.setAttribute("pageProduct", pageProduct2);
                    request.setAttribute("listProduct", listProduct2);
                    request.setAttribute("productTypesID", productTypes.getTypeId());
                    request.setAttribute("listCategories", categoriesFacade.findAll());
                    request.getRequestDispatcher("typeDetail.jsp").forward(request, response);
                    break;
                case "productDetail":
                    request.setAttribute("product", TechLineUtils.buildProduct(productsFacade.find(request.getParameter("idProduct"))));
                    request.setAttribute("listCategories", categoriesFacade.findAll());
                    request.getRequestDispatcher("productDetail.jsp").forward(request, response);
                    break;

                case "showCustomer":
                    List<Customers> listCus = new ArrayList<>();
                    listCus = customersFacade.findAll();
                    PageProduct pageCustomer = new PageProduct(listCus, 5);
                    String nCus = request.getParameter("btn");
                    if (nCus != null) {
                        if (nCus.equals("next")) {
                            pageCustomer.next();
                        }
                        if (nCus.equals("prev")) {
                            pageCustomer.prev();
                        }
                    }
                    String pagesCus = request.getParameter("page");
                    if (pagesCus != null) {
                        int m = Integer.parseInt(pagesCus);
                        pageCustomer.setPageIndex(m);
                        pageCustomer.updateModel();
                    }
                    request.setAttribute("pageCus", pageCustomer);           
                    request.getRequestDispatcher("admin/customer.jsp").forward(request, response);
                    break;
                case "showSeller":
                    List<Seller> sellers = new ArrayList<>();
                    sellers = sellerFacadeLocal.findAll();
                    PageProduct pageSeller = new PageProduct(sellers, 5);
                    String nSeller = request.getParameter("btn");
                    if (nSeller != null) {
                        if (nSeller.equals("next")) {
                            pageSeller.next();
                        }
                        if (nSeller.equals("prev")) {
                            pageSeller.prev();
                        }
                    }
                    String pageSell = request.getParameter("page");
                    if (pageSell != null) {
                        int m = Integer.parseInt(pageSell);
                        pageSeller.setPageIndex(m);
                        pageSeller.updateModel();
                    }
                    request.setAttribute("pageSeller", pageSeller);           
                    request.getRequestDispatcher("admin/seller.jsp").forward(request, response);
                    break;
                case "showProductAdmin":
                    List<Products> listDateposted = productsFacade.getListProductByDatePost();
                    PageProduct pageProduct3 = new PageProduct(TechLineUtils.buildProductAdmin(listDateposted), 10);
                    String n3 = request.getParameter("btn");
                    if (n3 != null) {
                        if (n3.equals("next")) {
                            pageProduct3.next();
                        }
                        if (n3.equals("prev")) {
                            pageProduct3.prev();
                        }
                    }
                    String pages3 = request.getParameter("page");
                    if (pages3 != null) {
                        int m = Integer.parseInt(pages3);
                        pageProduct3.setPageIndex(m);
                        pageProduct3.updateModel();
                    }
                    request.setAttribute("pageProduct", pageProduct3);
                    request.getRequestDispatcher("admin/product.jsp").forward(request, response);
                    break;

                case "showCategories":
                    request.setAttribute("listCategories", categoriesFacade.findAll());
                    request.getRequestDispatcher("admin/categories.jsp").forward(request, response);
                    break;

                case "showProductType":
                    List<ProductTypes> listProductType = productTypesFacade.findAll();
                    PageProduct pageProductType = new PageProduct(listProductType, 10);
                    String nProductType = request.getParameter("btn");
                    if (nProductType != null) {
                        if (nProductType.equals("next")) {
                            pageProductType.next();
                        }
                        if (nProductType.equals("prev")) {
                            pageProductType.prev();
                        }
                    }
                    String pagesProductType = request.getParameter("page");
                    if (pagesProductType != null) {
                        int m = Integer.parseInt(pagesProductType);
                        pageProductType.setPageIndex(m);
                        pageProductType.updateModel();
                    }
                    request.setAttribute("pageProductType", pageProductType);
                    request.getRequestDispatcher("admin/type.jsp").forward(request, response);
                    break;

                case "showOrder":
                    request.setAttribute("listOrder", orderMasterFacade.findAll());
                    request.getRequestDispatcher("admin/order.jsp").forward(request, response);
                    break;

                case "homeAdmin":
                    long activeSellers = usersFacade.countActiveSeller();
                    long activeCustomers = usersFacade.countActiveCustomer();
                    long doneOrders = orderMasterFacade.countDoneOrder();
                    long productsSold = productsFacade.countSoldProduct();
                    List<Products> listProducts = productsFacade.getTopProduct();
                    List<TopProductModel> listTop = TechLineUtils.buildProductTop(listProducts);
                    List<Seller> listSeller = sellerFacadeLocal.findAll();
                    request.setAttribute("activeSellers", activeSellers);
                    request.setAttribute("activeCustomers", activeCustomers);
                    request.setAttribute("doneOrders", doneOrders);
                    request.setAttribute("productsSold", productsSold);
                    request.setAttribute("listTop", listTop.subList(0, 5));
                    request.setAttribute("listSeller", listSeller.subList(0, 4));
                    request.getRequestDispatcher("admin/home.jsp").forward(request, response);
                    break;

                case "Login":
                    Users users = usersFacade.checkLogin(request.getParameter("username"), request.getParameter("password"));
                    if (users == null) {
                        request.setAttribute("error", "Page not found");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    session.setAttribute("user", users);
                    request.getRequestDispatcher("HomeServlet").forward(request, response);
                    break;

                case "Logout":
                    session = request.getSession();
                    //destroy session
                    session.invalidate();
                    request.getRequestDispatcher("HomeServlet").forward(request, response);
                    break;
                case "homeSeller":
                    request.setAttribute("user", usersFacade.find(user.getUserId()));
                    request.getRequestDispatcher("seller/home.jsp").forward(request, response);
                    break;
                case "homeCustomer":
                    String userID = user.getUserId();
                    Users u = usersFacade.find(userID);
                    request.setAttribute("user", u);
                    Customers c = customersFacade.find(userID);
                    String birthday[] = c.getDob().split("/");
                    request.setAttribute("date", Integer.parseInt(birthday[0]));
                    request.setAttribute("month", Integer.parseInt(birthday[1]));
                    request.setAttribute("year", Integer.parseInt(birthday[2]));
                    List<Integer> listDate = new ArrayList<>();
                    List<Integer> listMonth = new ArrayList<>();
                    List<Integer> listYear = new ArrayList<>();
                    for (int i = 1; i < 32; i++) {
                        listDate.add(i);
                        if (i < 13) {
                            listMonth.add(i);
                        }
                    }
                    for (int i = 1950; i < 2018; i++) {
                        listYear.add(i);
                    }
                    request.setAttribute("listDate", listDate);
                    request.setAttribute("listMonth", listMonth);
                    request.setAttribute("listYear", listYear);
                    request.setAttribute("customer", c);
                    request.setAttribute("listCategories", categoriesFacade.findAll());
                    request.getRequestDispatcher("customer.jsp").forward(request, response);
                    break;
                case "sellerProduct":
                    List<Products> sellerProduct = productsFacade.getListProductBySeller(user.getUserId());
                    request.setAttribute("lsProduct", sellerProduct);
                    request.getRequestDispatcher("seller/product.jsp").forward(request, response);
                    break;
                case "sellerOrder":
                    TechLineUtils util = new TechLineUtils();
                    List<SellerOrder> order = util.getSellerOrdered(user.getUserId());
                    request.setAttribute("order", order);
                    request.getRequestDispatcher("seller/order.jsp").forward(request, response);
                    break;
                case "sellerProductDetail":
                    request.setAttribute("listBrand", brandsFacade.findAll());
                    request.setAttribute("listType", productTypesFacade.findAll());
                    request.setAttribute("productDetail", productsFacade.find(request.getParameter("productId")));
                    request.getRequestDispatcher("seller/editProduct.jsp").forward(request, response);
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
