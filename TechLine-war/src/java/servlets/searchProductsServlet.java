/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Categories;
import entities.CategoriesFacadeLocal;
import entities.ProductTypes;
import entities.Products;
import entities.ProductsFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class searchProductsServlet extends HttpServlet {

    @EJB
    private ProductsFacadeLocal productsFacade;
    @EJB
    private CategoriesFacadeLocal categoriesFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        List<Products> listProductSearch = new ArrayList<>();
        List<Categories> listCategories = categoriesFacade.showAll();
        try (PrintWriter out = response.getWriter()) {
            switch (action) {
                case "Search":
                    String keyword = request.getParameter("txtProductName");
                    listProductSearch = productsFacade.getListProductsByName(keyword);
                    List<ProductTypes> listTypeSearch = productsFacade.getListTypeByName(keyword);
                    ProductTypes fakeSelect = new ProductTypes("Select");
                    fakeSelect.setTypeName("Select");
                    listTypeSearch.add(0, fakeSelect);
                    getServletContext().setAttribute("listForward", listProductSearch);
                    getServletContext().setAttribute("keyword", keyword);
                    getServletContext().setAttribute("listTypeSearch", listTypeSearch);
                    request.setAttribute("listProductSearch", listProductSearch);
                    request.setAttribute("listCategories", listCategories);
                    request.getRequestDispatcher("search.jsp").forward(request, response);
                    break;
                case "byPrice":
                    String judge = "";
                    String strMin = request.getParameter("txtMin");
                    String strMax = request.getParameter("txtMax");
                    double min = 0, max = Double.MAX_VALUE;
                    if (!strMin.equals("") && strMax.equals("")) {
                        min = Double.parseDouble(strMin);
                    } else if (strMin.equals("") && !strMax.equals("")) {
                        max = Double.parseDouble(strMax);
                    } else {
                        min = Double.parseDouble(strMin);
                        max = Double.parseDouble(strMax);
                    }
                    listProductSearch = (List<Products>) getServletContext().getAttribute("listForward");
                    if (!listProductSearch.isEmpty()) {
                        List<Products> listByPrice = new ArrayList<>();
                        for (Products pro : listProductSearch) {
                            if (pro.getProductPrice() >= min && pro.getProductPrice() <= max) {
                                listByPrice.add(pro);
                            }
                        }
                        listProductSearch = new ArrayList<>(listByPrice);
                        request.setAttribute("listProductSearch", listProductSearch);
                    }
                    request.setAttribute("listCategories", categoriesFacade.showAll());
                    request.getRequestDispatcher("search.jsp").forward(request, response);
                    break;
                case "byType":
                    String typeIdSelected = request.getParameter("type");
                    listProductSearch = (List<Products>) getServletContext().getAttribute("listForward");
                    if (!listProductSearch.isEmpty()) {
                        List<Products> listByType = new ArrayList<>();
                        for (Products pro : listProductSearch) {
                            if (pro.getTypeId().getTypeId().equals(typeIdSelected)) {
                                listByType.add(pro);
                            }
                        }
                        listProductSearch = new ArrayList<>(listByType);
                        request.setAttribute("typeIdSelected", typeIdSelected);
                        request.setAttribute("listProductSearch", listProductSearch);
                    }
                    request.setAttribute("listCategories", categoriesFacade.showAll());
                    request.getRequestDispatcher("search.jsp").forward(request, response);
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
