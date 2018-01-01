/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.BrandsFacade;
import entities.ProductTypesFacadeLocal;
import entities.Products;
import entities.ProductsFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nth15
 */
public class editProductsServlet extends HttpServlet {

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
    private ProductsFacadeLocal productsFacadeLocal;

    @EJB
    private BrandsFacade brandsFacade;
    @EJB
    private ProductTypesFacadeLocal productTypesFacadeLocal;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String productId;
        Products product = null;
        Date today = null;
        switch (action) {
            case "sellerEditProductStatus":
                productId = request.getParameter("productId");
                product = productsFacadeLocal.find(productId);
                if (product.getProductStatus().toString().equals("true")) {
                    product.setProductStatus(false);
                } else {
                    product.setProductStatus(true);
                }
                productsFacadeLocal.edit(product);
                request.getRequestDispatcher("viewServlet?action=sellerProduct").forward(request, response);
                break;
            case "sellerEditProductDetail":
                productId = request.getParameter("productId");
                product = productsFacadeLocal.find(productId);
                today = new Date();
                product.setTypeId(productTypesFacadeLocal.find(request.getParameter("txtProductType")));
                product.setBrandId(brandsFacade.find(request.getParameter("txtBrand")));
                product.setProductName(request.getParameter("txtProductName"));
                product.setProductDesc(request.getParameter("txtDescription"));
                product.setProductSummary(request.getParameter("txtSummary"));
                product.setProductPrice(Double.parseDouble(request.getParameter("txtPrice")));
                product.setProductImage(request.getParameter("txtImage"));
                product.setProductUnit(request.getParameter("txtUnit"));
                product.setProductQuantity(Integer.parseInt(request.getParameter("txtQuantity")));
                product.setProductWeight(Double.parseDouble(request.getParameter("txtWeight")));
                product.setProductWidth(Double.parseDouble(request.getParameter("txtWidth")));
                product.setProductHeigth(Double.parseDouble(request.getParameter("txtHeight")));
                product.setProductLength(Double.parseDouble(request.getParameter("txtLength")));
                product.setProductDiscount(Integer.parseInt(request.getParameter("txtDiscount")));
                product.setIsApproved(true);
                product.setDatePosted(today);
                product.setProductStatus(true);
                productsFacadeLocal.edit(product);

                request.getRequestDispatcher("viewServlet?action=sellerProduct").forward(request, response);
                break;
            case "sellerCancelProduct":
                request.getRequestDispatcher("viewServlet?action=sellerProduct").forward(request, response);
                break;
            default:
                request.setAttribute("error", "Page not found");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                break;

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
