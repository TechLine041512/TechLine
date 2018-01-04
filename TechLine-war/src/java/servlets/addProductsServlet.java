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
import entities.Products;
import entities.ProductsComment;
import entities.ProductsCommentFacadeLocal;
import entities.ProductsFacadeLocal;
import entities.Users;
import entities.UsersFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.TechLineUtils;

public class addProductsServlet extends HttpServlet {
    @EJB
    private UsersFacadeLocal usersFacade;
    @EJB
    private ProductsCommentFacadeLocal productsCommentFacade;
    @EJB
    private CategoriesFacadeLocal categoriesFacade;

    @EJB
    private ProductTypesFacadeLocal productTypesFacade;
    @EJB
    private BrandsFacadeLocal brandsFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            Date today = null;
            Brands brands = null;
            ProductTypes productTypes = null;
            Products products = null;
            Users user = (Users) request.getSession().getAttribute("user");
            switch (action) {
                case "addProduct":
                    today = new Date();
                    brands = brandsFacade.find(request.getParameter("txtBrand"));
                    productTypes = productTypesFacade.find(request.getParameter("txtProductType"));
                    products = new Products();
                    String productID = productsFacade.newProductID();
                    if (productID != null) {
                        products.setProductId(productID);
                    }
                    products.setTypeId(productTypes);
                    products.setBrandId(brands);
                    products.setProductName(request.getParameter("txtProductName"));
                    products.setProductDesc(request.getParameter("txtDescription"));
                    products.setProductSummary(request.getParameter("txtSummary"));
                    products.setProductPrice(Double.parseDouble(request.getParameter("txtPrice")));
                    products.setProductImage(request.getParameter("txtImage"));
                    products.setProductUnit(request.getParameter("txtUnit"));
                    products.setProductQuantity(Integer.parseInt(request.getParameter("txtQuantity")));
                    products.setProductWeight(Double.parseDouble(request.getParameter("txtWeight")));
                    products.setProductWidth(Double.parseDouble(request.getParameter("txtWidth")));
                    products.setProductHeigth(Double.parseDouble(request.getParameter("txtHeight")));
                    products.setProductLength(Double.parseDouble(request.getParameter("txtLength")));
                    products.setProductDiscount(0);
                    products.setProductRating(0.0);
                    products.setIsApproved(true);
                    products.setDatePosted(today);
                    products.setProductStatus(true);
                    productsFacade.create(products);
                    request.getRequestDispatcher("viewServlet?action=showProductAdmin").forward(request, response);
                    break;
                case "cancelProduct":
                    request.getRequestDispatcher("viewServlet?action=showProductAdmin").forward(request, response);
                    break;
                    
                case "addCategory":
                    Categories categories = new Categories();
                    String catId = categoriesFacade.newId();
                    if (catId != null) {
                        categories.setCategoryId(catId);
                    }
                    categories.setCategoryName(request.getParameter("txtCategoryName"));
                    categories.setCategoryDesc(request.getParameter("txtDescription"));
                    categories.setCategoryStatus(true);
                    categories.setCategoryIcon(request.getParameter("txtIcon"));
                    categoriesFacade.create(categories);
                    request.getRequestDispatcher("viewServlet?action=showCategories").forward(request, response);
                    break;
                case "cancelCategories":
                    request.getRequestDispatcher("viewServlet?action=showCategories").forward(request, response);
                    break;
                    
                case "addProductType":
                    Categories categories2 = categoriesFacade.find(request.getParameter("txtCategory"));
                    ProductTypes productTypes2 = new ProductTypes();
                    String typeId = productTypesFacade.newId();
                    if (typeId != null) {
                        productTypes2.setTypeId(typeId);
                    }
                    productTypes2.setCategoryId(categories2);
                    productTypes2.setTypeName(request.getParameter("txtTypeName"));
                    productTypes2.setTypeIcon(request.getParameter("txtTypeIcon"));
                    productTypes2.setTypeDesc(request.getParameter("txtTypeDesc"));
                    productTypes2.setTypeStatus(true);
                    productTypesFacade.create(productTypes2);
                    request.getRequestDispatcher("viewServlet?action=showProductType").forward(request, response);
                    break;
                case "cancelProductType":
                    request.getRequestDispatcher("viewServlet?action=showProductType").forward(request, response);
                    break;
                case "comment":
                    String id = request.getParameter("productID");
                    products = productsFacade.find(id);
                    Users users = usersFacade.find(session.getAttribute("user"));
                    ProductsComment productsComment = new ProductsComment();
                    productsComment.setCommentID("Comment1");
                    productsComment.setProductId(products);
                    productsComment.setUserId(users);
                    productsComment.setCommentContent("txtContent");
                    productsComment.setCommentStatus(true);
                    request.getRequestDispatcher("viewServlet?action=showProductType&idProduct="+id).forward(request, response);
                    break;
                case "sellerAddProduct":
                    today = new Date();
                    brands = brandsFacade.find(request.getParameter("txtBrand"));
                    productTypes = productTypesFacade.find(request.getParameter("txtProductType"));
                    products = new Products();
                    String productID2 = productsFacade.newProductID();
                    if (productID2 != null) {
                        products.setProductId(productID2);
                    }
                    products.setProductId(request.getParameter("txtProductID"));
                    products.setUserId(user);
                    products.setTypeId(productTypes);
                    products.setBrandId(brands);
                    products.setProductName(request.getParameter("txtProductName"));
                    products.setProductDesc(request.getParameter("txtDescription"));
                    products.setProductSummary(request.getParameter("txtSummary"));
                    products.setProductPrice(Double.parseDouble(request.getParameter("txtPrice")));
                    products.setProductImage(request.getParameter("txtImage"));
                    products.setProductUnit(request.getParameter("txtUnit"));
                    products.setProductQuantity(Integer.parseInt(request.getParameter("txtQuantity")));
                    products.setProductWeight(Double.parseDouble(request.getParameter("txtWeight")));
                    products.setProductWidth(Double.parseDouble(request.getParameter("txtWidth")));
                    products.setProductHeigth(Double.parseDouble(request.getParameter("txtHeight")));
                    products.setProductLength(Double.parseDouble(request.getParameter("txtLength")));
                    products.setProductDiscount(0);
                    products.setProductRating(0.0);
                    products.setIsApproved(true);
                    products.setDatePosted(today);
                    products.setProductStatus(true);
                    productsFacade.create(products);
                    user.getProductsCollection().add(products);
                    request.getRequestDispatcher("viewServlet?action=sellerProduct").forward(request, response);
                    break;
                case "sellerCancelProduct":
                    request.getRequestDispatcher("viewServlet?action=sellerProduct").forward(request, response);
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
