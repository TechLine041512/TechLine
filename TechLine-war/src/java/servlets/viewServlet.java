/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Brands;
import entities.BrandsFacadeLocal;
import entities.Categories;
import entities.CategoriesFacadeLocal;
import entities.Customers;
import entities.CustomersFacadeLocal;
import entities.OrderDetailsFacadeLocal;
import entities.OrderMaster;
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
import models.ProductInCart;
import models.TopProductModel;
import utils.TechLineUtils;
import utils.PageProduct;
import models.SellerOrder;
import org.apache.commons.lang3.StringUtils;

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
            session.setAttribute("user", user);

            List<Products> listProduct = new ArrayList<>();
            List<Products> listProductRelated = new ArrayList<>();
            List<Categories> listCategories = categoriesFacade.showAll();
            List<Brands> listBrands = brandsFacade.showAll();
            List<OrderMaster> listOrderMaster = orderMasterFacade.findAll();
            List<Customers> listCustomer;
            List<Seller> listSellers;
            List<ProductTypes> listProductTypes;
            List<TopProductModel> listTopProducts = TechLineUtils.buildProductTop(productsFacade.getTopProduct());

            ProductTypes productTypes;
            PageProduct pageProduct;
            Products product;
            String idProduct;
            String typeId;
            switch (action) {

                case "cateDetail":
                    Categories categories = categoriesFacade.find(request.getParameter("idCate"));
                    listProductTypes = (List<ProductTypes>) categories.getProductTypesCollection();
                    for (ProductTypes p : listProductTypes) {
                        listProduct.addAll(p.getProductsCollection());
                    }
                    pageProduct = new PageProduct(TechLineUtils.buidProductIndexModel(listProduct), 12);
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
                    request.setAttribute("listTopProduct", listTopProducts.subList(0, 3));
                    request.setAttribute("category", categories);
                    request.setAttribute("listCategories", listCategories);
                    request.getRequestDispatcher("categoryDetail.jsp").forward(request, response);
                    break;

                case "typeDetail":
                    typeId = request.getParameter("idType");
                    productTypes = new ProductTypes();
                    if (StringUtils.isNotBlank(typeId)) {
                        productTypes = productTypesFacade.find(typeId);
                    }
                    listProduct = (List<Products>) productTypes.getProductsCollection();

                    pageProduct = new PageProduct(TechLineUtils.buidProductIndexModel(listProduct), 6);
                    String n1 = request.getParameter("btn");

                    if (n1 != null) {
                        if (n1.equals("next")) {
                            pageProduct.next();
                        }
                        if (n1.equals("prev")) {
                            pageProduct.prev();
                        }
                    }
                    String pages1 = request.getParameter("page");

                    if (pages1 != null) {
                        int m = Integer.parseInt(pages1);
                        pageProduct.setPageIndex(m);
                        pageProduct.updateModel();
                    }

                    request.setAttribute("pageProduct", pageProduct);
                    request.setAttribute("listProduct", listProduct);
                    request.setAttribute("listTopProduct", listTopProducts.subList(0,3));
                    request.setAttribute("productTypesID", productTypes.getTypeId()); //Noted
                    request.setAttribute("listCategories", listCategories);
                    request.getRequestDispatcher("typeDetail.jsp").forward(request, response);
                    break;

                case "productDetail":
                    idProduct = request.getParameter("idProduct");
                    product = new Products();
                    if (StringUtils.isNotBlank(idProduct)) {
                        product = productsFacade.find(idProduct);
                    }
                    if (product != null) {
                        request.setAttribute("product", TechLineUtils.buildProduct(product));
                        productTypes = product.getTypeId();
                        listProductRelated.addAll(productTypes.getProductsCollection());
                        if (listProductRelated.size() > 4) {
                            request.setAttribute("listProductRelated1", TechLineUtils.buidProductIndexModel(listProductRelated).subList(0, 4));
                            request.setAttribute("listProductRelated2", TechLineUtils.buidProductIndexModel(listProductRelated).subList(4, listProductRelated.size()));
                        } else {
                            request.setAttribute("listProductRelated1", TechLineUtils.buidProductIndexModel(listProductRelated).subList(0, listProductRelated.size()));
                        }
                    }
                    request.setAttribute("listTopProduct", listTopProducts.subList(0, 3));
                    request.setAttribute("listCategories", listCategories);
                    request.getRequestDispatcher("productDetail.jsp").forward(request, response);
                    break;

                case "showCustomer":
                    listCustomer = customersFacade.showAll();
                    PageProduct pageCustomer = new PageProduct(listCustomer, 5);
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
                    listSellers = sellerFacadeLocal.showAll();
                    PageProduct pageSeller = new PageProduct(listSellers, 5);
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
                    pageProduct = new PageProduct(TechLineUtils.buildProductAdmin(listDateposted), 10);
                    String n3 = request.getParameter("btn");
                    if (n3 != null) {
                        if (n3.equals("next")) {
                            pageProduct.next();
                        }
                        if (n3.equals("prev")) {
                            pageProduct.prev();
                        }
                    }
                    String pages3 = request.getParameter("page");
                    if (pages3 != null) {
                        int m = Integer.parseInt(pages3);
                        pageProduct.setPageIndex(m);
                        pageProduct.updateModel();
                    }
                    request.setAttribute("pageProduct", pageProduct);
                    request.getRequestDispatcher("admin/product.jsp").forward(request, response);
                    break;

                case "showBrand":
                    pageProduct = new PageProduct(listBrands, 10);
                    String nBrand = request.getParameter("btn");
                    if (nBrand != null) {
                        if (nBrand.equals("next")) {
                            pageProduct.next();
                        }
                        if (nBrand.equals("prev")) {
                            pageProduct.prev();
                        }
                    }
                    String pagesBrand = request.getParameter("page");
                    if (pagesBrand != null) {
                        int m = Integer.parseInt(pagesBrand);
                        pageProduct.setPageIndex(m);
                        pageProduct.updateModel();
                    }
                    request.setAttribute("pageBrands", pageProduct);
                    request.getRequestDispatcher("admin/brand.jsp").forward(request, response);
                    break;

                case "showCategories":
                    request.setAttribute("listCategories", listCategories);
                    request.getRequestDispatcher("admin/categories.jsp").forward(request, response);
                    break;

                case "showProductType":
                    listProductTypes = productTypesFacade.showAll();
                    pageProduct = new PageProduct(listProductTypes, 10);
                    String nProductType = request.getParameter("btn");
                    if (nProductType != null) {
                        if (nProductType.equals("next")) {
                            pageProduct.next();
                        }
                        if (nProductType.equals("prev")) {
                            pageProduct.prev();
                        }
                    }
                    String pagesProductType = request.getParameter("page");
                    if (pagesProductType != null) {
                        int m = Integer.parseInt(pagesProductType);
                        pageProduct.setPageIndex(m);
                        pageProduct.updateModel();
                    }
                    request.setAttribute("pageProductType", pageProduct);
                    request.getRequestDispatcher("admin/type.jsp").forward(request, response);
                    break;

                case "showOrder":

                    pageProduct = new PageProduct(listOrderMaster, 15);
                    String nOrderMaster = request.getParameter("btn");
                    if (nOrderMaster != null) {
                        if (nOrderMaster.equals("next")) {
                            pageProduct.next();
                        }
                        if (nOrderMaster.equals("prev")) {
                            pageProduct.prev();
                        }
                    }
                    String pagesOrderMaster = request.getParameter("page");

                    if (pagesOrderMaster != null) {
                        int m = Integer.parseInt(pagesOrderMaster);
                        pageProduct.setPageIndex(m);
                        pageProduct.updateModel();
                    }

                    request.setAttribute("pageOrderMaster", pageProduct);
                    request.getRequestDispatcher("admin/order.jsp").forward(request, response);
                    break;

                case "homeAdmin":
                    long activeSellers = usersFacade.countActiveSeller();
                    long activeCustomers = usersFacade.countActiveCustomer();
                    long doneOrders = orderMasterFacade.countDoneOrder();
                    long productsSold = productsFacade.countSoldProduct();
                    listSellers = sellerFacadeLocal.findAll();

                    request.setAttribute("activeSellers", activeSellers);
                    request.setAttribute("activeCustomers", activeCustomers);
                    request.setAttribute("doneOrders", doneOrders);
                    request.setAttribute("productsSold", productsSold);

                    if (listTopProducts.size() > 5) {
                        listTopProducts = listTopProducts.subList(0, 5);
                    }

                    if (listSellers.size() > 4) {
                        listSellers = listSellers.subList(0, 4);
                    }

                    request.setAttribute("listTop", listTopProducts);
                    request.setAttribute("listSeller", listSellers);
                    request.getRequestDispatcher("admin/home.jsp").forward(request, response);
                    break;

                case "Login":
                    String userName = request.getParameter("username");
                    String password = request.getParameter("password");
                    Users users = usersFacade.checkLogin(userName, password);
                    if (users == null) {
                        request.setAttribute("message", "Invalid Users or Password");
                    } else {
                        session.setAttribute("user", users);
                    }
                    request.getRequestDispatcher("HomeServlet").forward(request, response);
                    break;

                case "Logout":
                    session = request.getSession();
                    //destroy session
                    session.invalidate();
                    request.getRequestDispatcher("HomeServlet").forward(request, response);
                    break;

                case "homeSeller":
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("seller/home.jsp").forward(request, response);
                    break;

                case "homeCustomer":
                    request.setAttribute("user", user);
                    String userId = user.getUserId();
                    Customers c = customersFacade.find(userId);
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
                    request.setAttribute("listCategories", listCategories);
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
                    request.setAttribute("listBrand", listBrands);
                    request.setAttribute("listType", productTypesFacade.showAll());
                    request.setAttribute("productDetail", productsFacade.find(request.getParameter("productId")));
                    request.getRequestDispatcher("seller/editProduct.jsp").forward(request, response);
                    break;

                case "viewShoppingCart":
                    if (user == null) {
                        request.setAttribute("message", "Please Log in first");
                        request.getRequestDispatcher("HomeServlet").forward(request, response);
                    }
                    Customers customer = user.getCustomers();
                    if (customer == null) {
                        request.setAttribute("message", "Please register customer account");
                        request.getRequestDispatcher("HomeServlet").forward(request, response);
                    }
                    List<ProductInCart> cart = (List<ProductInCart>) session.getAttribute("cart");
                    double subTotal = 0;
                    if (cart != null) {
                        for (ProductInCart p : cart) {
                            subTotal += p.getTotal();
                        }

                        int point = user.getCustomers().getPoint();
                        int discount = point / 10;
                        request.setAttribute("memberDiscount", discount);
                        request.setAttribute("subtotal", subTotal);
                        request.setAttribute("cart", cart);
                    } else {
                        request.setAttribute("message", "Please select some products");
                        request.getRequestDispatcher("HomeServlet").forward(request, response);
                    }
                    request.setAttribute("listCategories", listCategories);
                    request.getRequestDispatcher("cart.jsp").forward(request, response);
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
