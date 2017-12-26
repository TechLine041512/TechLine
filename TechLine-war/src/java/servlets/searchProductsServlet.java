/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import entities.BrandsFacadeLocal;
import entities.CategoriesFacadeLocal;
import entities.ProductTypesFacadeLocal;
import entities.ProductsFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nth15
 */
public class searchProductsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @EJB
    ProductsFacadeLocal productsFacadeLocal;
    
    @EJB
    BrandsFacadeLocal brandsFacadeLocal;
    
    @EJB
    ProductTypesFacadeLocal typesFacadeLocal;
    
    @EJB
    CategoriesFacadeLocal categoriesFacadeLocal;
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            switch (action) {
                case "ProductList": 
                    request.setAttribute("lsProductEnabble", productsFacadeLocal.findAll().subList(0, 9));
                    request.setAttribute("lsProductDisable", productsFacadeLocal.findAll().subList(0, 9));
                    request.getRequestDispatcher("admin/product.jsp").forward(request, response);
                    break;
                 case "CategoriesList": 
                    request.setAttribute("lsCategoryEnabble", categoriesFacadeLocal.findAll().subList(0, 9));
                    request.setAttribute("lsCategoryDisable", categoriesFacadeLocal.findAll().subList(0, 9));
                    request.setAttribute("lsBrandEnable", brandsFacadeLocal.findAll().subList(0, 9));
                    request.setAttribute("lsBrandDisable", brandsFacadeLocal.findAll().subList(0, 9));
                    request.getRequestDispatcher("admin/categories.jsp").forward(request, response);
                    break; 
                 case "ProductTypeList": 
                    request.setAttribute("listPrdTypeEnable", typesFacadeLocal.findAll().subList(0, 9));
                    request.setAttribute("listPrdTypeDisable", typesFacadeLocal.findAll().subList(0, 9));
                    request.getRequestDispatcher("admin/type.jsp").forward(request, response);
                    break;       
                 
                default : break;
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
