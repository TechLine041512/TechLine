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
import entities.ProductsEditHistory;
import entities.ProductsEditHistoryFacadeLocal;
import entities.ProductsEditHistoryPK;
import entities.ProductsFacadeLocal;
import java.io.IOException;
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
    @EJB
    private CategoriesFacadeLocal categoriesFacade;

    @EJB
    private ProductsEditHistoryFacadeLocal productsEditHistoryFacade;
    @EJB
    private ProductTypesFacadeLocal productTypesFacade;
    @EJB
    private BrandsFacadeLocal brandsFacade;
    
    @EJB
    private ProductsFacadeLocal productsFacade;
    
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
                product = productsFacade.find(productId);
                if (product.getProductStatus()) {
                    product.setProductStatus(false);
                } else {
                    product.setProductStatus(true);
                }
                productsFacade.edit(product);
                request.getRequestDispatcher("viewServlet?action=sellerProduct").forward(request, response);
                break;
            case "sellerEditProductDetail":
                productId = request.getParameter("productId");
                product = productsFacade.find(productId);
                today = new Date();
                product.setTypeId(productTypesFacade.find(request.getParameter("txtProductType")));
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
                productsFacade.edit(product);

                request.getRequestDispatcher("viewServlet?action=sellerProduct").forward(request, response);
                break;
            case "sellerCancelProduct":
                request.getRequestDispatcher("viewServlet?action=sellerProduct").forward(request, response);
                break;
            case "editProduct":
                productId = request.getParameter("txtProductID");
                ProductsEditHistoryPK editHistoryPK = new ProductsEditHistoryPK(productId, 1);
                ProductsEditHistory editHistory = productsEditHistoryFacade.find(editHistoryPK);
                //Check whether this product has eidt history or not. If not, create new
                if (editHistory != null) {
                    int version = productsEditHistoryFacade.newVersion(productId);
                    editHistoryPK = new ProductsEditHistoryPK(productId, version);
                }
                editHistory = new ProductsEditHistory(editHistoryPK);
                //Save current information of product to history
                Products products = productsFacade.find(productId);
                java.sql.Date getToday = new java.sql.Date(new Date().getTime());//return current time
                editHistory.setProductName(products.getProductName());
                editHistory.setProductPrice(products.getProductPrice());
                editHistory.setProductDiscount(products.getProductDiscount());
                editHistory.setEditTime(getToday);
                productsEditHistoryFacade.create(editHistory);
                //Edit product
                Brands brands = brandsFacade.find(request.getParameter("txtBrand"));
                ProductTypes productTypes = productTypesFacade.find(request.getParameter("txtProductType"));
                products.setTypeId(productTypes);
                products.setBrandId(brands);
                products.setProductName(request.getParameter("txtProductName"));
                products.setProductDesc(request.getParameter("txtDescription"));
                products.setProductSummary(request.getParameter("txtSummary"));
                products.setProductPrice(Double.parseDouble(request.getParameter("txtPrice")));
                products.setProductUnit(request.getParameter("txtUnit"));
                String weight = request.getParameter("txtWeight");
                if(weight.equals("")){
                    weight = "0";
                }
                products.setProductWeight(Double.parseDouble(weight));
                String width = request.getParameter("txtWidth");
                if(width.equals("")){
                    width = "0";
                }
                products.setProductWidth(Double.parseDouble(width));
                String height = request.getParameter("txtHeight");
                if(height.equals("")){
                    height = "0";
                }
                products.setProductHeigth(Double.parseDouble(height));
                String length = request.getParameter("txtLength");
                if(length.equals("")){
                    length = "0";
                }
                products.setProductLength(Double.parseDouble(length));
                products.setProductQuantity(Integer.parseInt(request.getParameter("txtQuantity")));
                products.setProductImage(request.getParameter("txtImage"));
                String discount = request.getParameter("txtDiscount");
                if(discount.equals("")){
                    discount = "0";
                }
                products.setProductDiscount(Integer.parseInt(discount));
                productsFacade.edit(products);
                request.getRequestDispatcher("viewServlet?action=showProductAdmin").forward(request, response);
                break;
            case "cancelProduct":
                request.getRequestDispatcher("viewServlet?action=showProductAdmin").forward(request, response);
                break;
            //admin edit product type
            case "editProductType":
                String typeId = request.getParameter("txtTypeId");
                ProductTypes type = productTypesFacade.find(typeId);
                type.setTypeName(request.getParameter("txtTypeName"));
                Categories cat = categoriesFacade.find(request.getParameter("txtCategory"));
                type.setCategoryId(cat);
                type.setTypeDesc(request.getParameter("txtTypeDesc"));
                type.setTypeIcon(request.getParameter("txtTypeIcon"));
                productTypesFacade.edit(type);
                request.getRequestDispatcher("viewServlet?action=showProductType").forward(request, response);
                break;
            //admin cancel product type
            case "cancelProductType":
                request.getRequestDispatcher("viewServlet?action=showProductType").forward(request, response);
                break;
            //admin edit category
            case "editCategory":
                String catId = request.getParameter("txtCategoryId");
                Categories catEdit = categoriesFacade.find(catId);
                catEdit.setCategoryName(request.getParameter("txtCategoryName"));
                catEdit.setCategoryDesc(request.getParameter("txtDescription"));
                catEdit.setCategoryIcon(request.getParameter("txtIcon"));
                categoriesFacade.edit(catEdit);
                request.getRequestDispatcher("viewServlet?action=showCategories").forward(request, response);
                break;
            //admin cancel category
            case "cancelCategory":
                request.getRequestDispatcher("viewServlet?action=showCategories").forward(request, response);
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
