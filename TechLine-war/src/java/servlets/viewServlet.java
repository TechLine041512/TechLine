/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.BrandsFacadeLocal;
import entities.Categories;
import entities.CategoriesFacadeLocal;
import entities.CustomersFacadeLocal;
import entities.OrderMasterFacadeLocal;
import entities.ProductTypes;
import entities.ProductTypesFacadeLocal;
import entities.Products;
import entities.ProductsCommentFacadeLocal;
import entities.ProductsFacadeLocal;
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
import utils.TechLineUtils;
import utils.PageProduct;

/**
 *
 * @author nth15
 */
public class viewServlet extends HttpServlet {

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
                    PageProduct pageProduct = new PageProduct(TechLineUtils.buidProductIndexModel(listProduct));
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
                    PageProduct pageProduct2 = new PageProduct(TechLineUtils.buidProductIndexModel(listProduct2));
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

                case "showUser":
                    request.setAttribute("listCustomer", usersFacade.getListCustomer());
                    request.setAttribute("listSeller", usersFacade.getListSeller());
                    request.getRequestDispatcher("admin/customer.jsp").forward(request, response);
                    break;

                case "showProductAdmin":
                    request.setAttribute("listProduct", productsFacade.getListProductByDatePost());
                    request.setAttribute("listProductSeller", productsFacade.getListProductSeller());
                    request.getRequestDispatcher("admin/product.jsp").forward(request, response);
                    break;

                case "showCategories":
                    request.setAttribute("listCategories", categoriesFacade.findAll());
                    request.getRequestDispatcher("admin/categories.jsp").forward(request, response);
                    break;

                case "showProductType":
                    request.setAttribute("listProductType", productTypesFacade.findAll());
                    request.getRequestDispatcher("admin/type.jsp").forward(request, response);
                    break;

                case "showOrder":
                    request.setAttribute("listOrder", orderMasterFacade.findAll());
                    request.getRequestDispatcher("admin/order.jsp").forward(request, response);
                    break;

                case "homeAdmin":
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
                case "register":
                    String idRegist = request.getParameter("txtUsername");
                    List<Users> listUserRegist = usersFacade.findAll();
                    for(Users usn: listUserRegist) {
                        if(usn.getUserId().equals(idRegist)) {
                            request.setAttribute("registMess", "Username already exists!");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                            break;
                        }
                    }
                    Users userRegist = new Users();
                    userRegist.setUserId(idRegist);
                    userRegist.setPassword(request.getParameter("txtPassword"));
                    userRegist.setEmail(request.getParameter("txtEmail"));
                    userRegist.setFullname(request.getParameter("txtFullname"));
                    userRegist.setPhone(request.getParameter("txtPhone"));
                    String roleRegist = request.getParameter("role");
                    userRegist.setRole(roleRegist);
                    userRegist.setUserStatus(true);
                    usersFacade.create(userRegist);
                    request.setAttribute("user", userRegist);
                    request.setAttribute("registMess", "Registration successful!");
                    if (roleRegist.equals("customer")) {
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else if (roleRegist.equals("seller")) {
                        request.getRequestDispatcher("seller/home.jsp").forward(request, response);
                    }
                    break;
                case "homeSeller":
                    request.setAttribute("user", usersFacade.find(user.getUserId()));
                    request.getRequestDispatcher("seller/home.jsp").forward(request, response);
                    break;
                case "homeCustomer":
                    request.setAttribute("user", usersFacade.find(user.getUserId()));
                    request.setAttribute("customer", customersFacade.find(user.getUserId()));
                    String birthday[] = customersFacade.find(user.getUserId()).getDob().split("/");
                    request.setAttribute("date", Integer.parseInt(birthday[0]));
                    request.setAttribute("month", Integer.parseInt(birthday[1]));
                    request.setAttribute("year", Integer.parseInt(birthday[2]));
                    ArrayList<Integer> listDate = new ArrayList<>();
                    ArrayList<Integer> listMonth = new ArrayList<>();
                    ArrayList<Integer> listYear = new ArrayList<>();
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
                    request.getRequestDispatcher("customer.jsp").forward(request, response);
                    break;
                case "sellerProduct":
                    List<Products> sellerProduct = productsFacade.getListProductBySeller(user.getUserId());
                    request.setAttribute("lsProduct", sellerProduct);
                    request.getRequestDispatcher("seller/product.jsp").forward(request, response);
                    break;
                case "sellerOrder":
                    request.setAttribute("listOrder", orderMasterFacade.findAll());
                    request.setAttribute("product", productsFacade.getListProductBySeller(user.getUserId()));
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
