/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Categories;
import entities.CategoriesFacadeLocal;
import entities.ProductTypes;
import entities.ProductTypesFacadeLocal;
import entities.Products;
import entities.ProductsFacadeLocal;
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

/**
 *
 * @author nth15
 */
public class viewServlet extends HttpServlet {

    @EJB
    private UsersFacadeLocal usersFacade;
    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;
    @EJB
    private ProductTypesFacadeLocal productTypesFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            switch (action) {
                case "cateDetail":
                    Categories categories = categoriesFacade.find(request.getParameter("idCate"));
                    List<Products> listProduct = new ArrayList<>();
                    List<ProductTypes> listProductTypes = (List<ProductTypes>) categories.getProductTypesCollection();
                    for (ProductTypes productTypes : listProductTypes) {
                        listProduct.addAll(productTypes.getProductsCollection());
                    }
                    request.setAttribute("listProduct", listProduct);
                    request.setAttribute("listCategories", categoriesFacade.findAll());
                    request.getRequestDispatcher("categoryDetail.jsp").forward(request, response);
                    break;

                case "productDetail":
                    request.setAttribute("product", productsFacade.find(request.getParameter("idProduct")));
                    request.setAttribute("listCategories", categoriesFacade.findAll());
                    request.getRequestDispatcher("productDetail.jsp").forward(request, response);
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
