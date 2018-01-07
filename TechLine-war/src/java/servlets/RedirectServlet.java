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
import entities.ProductTypes;
import entities.ProductTypesFacadeLocal;
import entities.ProductsFacadeLocal;
import entities.Users;
import entities.UsersFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedirectServlet extends HttpServlet {
    @EJB
    private UsersFacadeLocal usersFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;
    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    @EJB
    private ProductTypesFacadeLocal productTypesFacade;
    @EJB
    private BrandsFacadeLocal brandsFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            List<Categories> listCategories = categoriesFacade.showAll();
            List<Brands> listBrands = brandsFacade.showAll();
            List<ProductTypes> listProductTypes = productTypesFacade.showAll();
            switch (action) {
                case "backToHome":
                    HttpSession session = request.getSession();
                    Users u = (Users) session.getAttribute("user");
                    session.setAttribute("user", u);
                    request.getRequestDispatcher("HomeServlet").forward(request, response);
                case "addProduct":
                    request.setAttribute("listBrand", listBrands);
                    request.setAttribute("listType", listProductTypes);
                    request.getRequestDispatcher("admin/addProduct.jsp").forward(request, response);
                    break;
                case "addCategory":
                    request.getRequestDispatcher("admin/addCategory.jsp").forward(request, response);
                    break;
                case "addProductType":
                    request.setAttribute("listCategory", listCategories);
                    request.getRequestDispatcher("admin/addType.jsp").forward(request, response);
                    break;
                case "editProduct":
                    request.setAttribute("listBrand", listBrands);
                    request.setAttribute("listType", listProductTypes);
                    String productId = request.getParameter("pid");
                    request.setAttribute("product", productsFacade.find(productId));
                    request.getRequestDispatcher("admin/editProduct.jsp").forward(request, response);
                    break;
                case "editProductType":
                    request.setAttribute("listCategory", listCategories);
                    String typeId = request.getParameter("typeId");
                    request.setAttribute("type", productTypesFacade.find(typeId));
                    request.getRequestDispatcher("admin/editType.jsp").forward(request, response);
                    break;
                case "editCategory":
                    String catId = request.getParameter("catId");
                    request.setAttribute("category", categoriesFacade.find(catId));
                    request.getRequestDispatcher("admin/editCategory.jsp").forward(request, response);
                    break;
                case "sellerAddProduct":
                    request.setAttribute("listBrand", listBrands);
                    request.setAttribute("listType", listProductTypes);
                    request.getRequestDispatcher("seller/addProduct.jsp").forward(request, response);
                    break;
                default:
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
