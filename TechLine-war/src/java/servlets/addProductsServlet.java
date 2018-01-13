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
                    //Append strings to save to database
                    String imageChain = request.getParameter("txtImage");
                    for (int i = 1; i < 5; i++) {
                        if (!request.getParameter("txtImage" + i).equals(""))
                            imageChain += "," + request.getParameter("txtImage" + i);
                    }
                    products.setProductImage(imageChain);
                    products.setProductUnit(request.getParameter("txtUnit"));
                    products.setProductQuantity(Integer.parseInt(request.getParameter("txtQuantity")));
                    if (!request.getParameter("txtWeight").equals(""))
                        products.setProductWeight(Double.parseDouble(request.getParameter("txtWeight")));
                    else
                        products.setProductWeight(0.0);
                    if (!request.getParameter("txtWidth").equals(""))
                        products.setProductWidth(Double.parseDouble(request.getParameter("txtWidth")));
                    else
                        products.setProductWidth(0.0);
                    if (!request.getParameter("txtHeight").equals(""))
                        products.setProductHeigth(Double.parseDouble(request.getParameter("txtHeight")));
                    else
                        products.setProductHeigth(0.0);
                    if (!request.getParameter("txtLength").equals(""))
                        products.setProductLength(Double.parseDouble(request.getParameter("txtLength")));
                    else
                        products.setProductLength(0.0);
                    String discountAdd = request.getParameter("txtDiscount");
                    if (!discountAdd.equals(""))
                        products.setProductDiscount(Integer.parseInt(discountAdd));
                    else
                        products.setProductDiscount(0);
                    products.setProductRating(0.0);
                    products.setIsApproved(true);
                    products.setDatePosted(today);
                    products.setProductStatus(true);
                    products.setUserId(user);
                    productsFacade.create(products);
                    productTypes.getProductsCollection().add(products);
                    productTypesFacade.edit(productTypes);
                    brands.getProductsCollection().add(products);
                    brandsFacade.edit(brands);
                    session.setAttribute("user", user);
                    request.setAttribute("message", "Add product successfully");
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
                    request.setAttribute("message", "Add category successfully");
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
                    categories2.getProductTypesCollection().add(productTypes2);
                    categoriesFacade.edit(categories2);
                    request.setAttribute("message", "Add type successfully");
                    request.getRequestDispatcher("viewServlet?action=showProductType").forward(request, response);
                    break;
                    
                case "addBrand":
                    Brands brand = new Brands();
                    String brandId = brandsFacade.newBrandId();
                    if (brandId != null) {
                        brand.setBrandId(brandId);
                    }
                    brand.setBrandIcon(request.getParameter("txtBrandIcon"));
                    brand.setBrandName(request.getParameter("txtBrandName"));
                    brand.setBrandStatus(Boolean.TRUE);
                    brandsFacade.create(brand);
                    request.setAttribute("message", "Add brand successfully");
                    request.getRequestDispatcher("viewServlet?action=showBrand").forward(request, response);
                    break;
                case "cancelProductType":
                    request.getRequestDispatcher("viewServlet?action=showProductType").forward(request, response);
                    break;
                case "comment":
                    String id = request.getParameter("productID");
                    String content = request.getParameter("commentContent");
                    products = productsFacade.find(id);
                    Users users = (Users) session.getAttribute("user");
                    if (users == null) {
                        request.setAttribute("message", "Please login first");
                    }
                    ProductsComment productsComment = new ProductsComment();
                    String commentId = productsCommentFacade.newId();
                    productsComment.setCommentID(commentId);
                    productsComment.setProductId(products);
                    productsComment.setUserId(users);
                    productsComment.setCommentContent(content);
                    productsComment.setCommentStatus(true);
                    productsCommentFacade.create(productsComment);
                    users.getProductsCommentCollection().add(productsComment);
                    usersFacade.edit(users);
                    products.getProductsCommentCollection().add(productsComment);
                    productsFacade.edit(products);
                    request.getRequestDispatcher("viewServlet?action=productDetail&idProduct="+id).forward(request, response);
                    break;

                case "sellerAddProduct":
                    today = new Date();
                    brands = brandsFacade.find(request.getParameter("txtBrand"));
                    productTypes = productTypesFacade.find(request.getParameter("txtProductType"));
                    products = new Products();
                    String productID2 = productsFacade.newProductID();
                    StringBuilder image = new StringBuilder();
                    image.append(request.getParameter("txtImageMain")).append(",");
                    image.append(request.getParameter("txtSubImage1")).append(",");
                    image.append(request.getParameter("txtSubImage2")).append(",");
                    image.append(request.getParameter("txtSubImage3")).append(",");
                    image.append(request.getParameter("txtSubImage4"));
                    if (productID2 != null) {
                        products.setProductId(productID2);
                    }
                    products.setUserId(user);
                    products.setTypeId(productTypes);
                    products.setBrandId(brands);
                    products.setProductName(request.getParameter("txtProductName"));
                    products.setProductDesc(request.getParameter("txtDescription"));
                    products.setProductSummary(request.getParameter("txtSummary"));
                    products.setProductPrice(Double.parseDouble(request.getParameter("txtPrice")));
                    products.setProductImage(image.toString());
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
