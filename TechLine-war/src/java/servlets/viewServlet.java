/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Brands;
import entities.BrandsFacadeLocal;
import entities.Categories;
import entities.CategoriesFacadeLocal;
import entities.Customers;
import entities.CustomersFacadeLocal;
import entities.OrderDetails;
import entities.OrderDetailsFacadeLocal;
import entities.OrderMaster;
import entities.OrderMasterFacadeLocal;
import entities.ProductTypes;
import entities.ProductTypesFacadeLocal;
import entities.Products;
import entities.ProductsCommentFacadeLocal;
import entities.ProductsEditHistory;
import entities.ProductsEditHistoryFacadeLocal;
import entities.ProductsFacadeLocal;
import entities.Seller;
import entities.SellerFacadeLocal;
import entities.Users;
import entities.UsersFacadeLocal;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.ChartModel;
import models.CustomerReportModel;
import models.OrderReportModel;
import models.ProductHistory;
import models.ProductInCart;
import models.TopProductModel;
import utils.TechLineUtils;
import utils.PageProduct;
import models.SellerOrder;
import models.SellerReportModel;
import models.TopProductStaticModel;
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
    @EJB
    private ProductsEditHistoryFacadeLocal productsEditHistory;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
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
            PageProduct paging;
            Products product;
            String idProduct;
            String typeId;
            Gson gson;
            switch (action) {

                case "cateDetail":
                    Categories categories = categoriesFacade.find(request.getParameter("idCate"));
                    listProductTypes = (List<ProductTypes>) categories.getProductTypesCollection();
                    for (ProductTypes p : listProductTypes) {
                        listProduct.addAll(p.getProductsCollection());
                    }
                    if (!listProduct.isEmpty()) {
                        paging = new PageProduct(TechLineUtils.buidProductIndexModel(listProduct), 12);
                        String n = request.getParameter("btn");
                        if (n != null) {
                            if (n.equals("next")) {
                                paging.next();
                            }
                            if (n.equals("prev")) {
                                paging.prev();
                            }
                        }
                        String pages = request.getParameter("page");
                        if (pages != null) {
                            int m = Integer.parseInt(pages);
                            paging.setPageIndex(m);
                            paging.updateModel();
                        }
                        request.setAttribute("pageProduct", paging);
                        request.setAttribute("listProduct", listProduct);
                    } else {
                        request.setAttribute("message", "This category haven't got any products yet");
                    }
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
                    if (!listProduct.isEmpty()) {
                        paging = new PageProduct(TechLineUtils.buidProductIndexModel(listProduct), 6);
                        String n1 = request.getParameter("btn");

                        if (n1 != null) {
                            if (n1.equals("next")) {
                                paging.next();
                            }
                            if (n1.equals("prev")) {
                                paging.prev();
                            }
                        }
                        String pages1 = request.getParameter("page");

                        if (pages1 != null) {
                            int m = Integer.parseInt(pages1);
                            paging.setPageIndex(m);
                            paging.updateModel();
                        }

                        request.setAttribute("productType", productTypes);
                        request.setAttribute("pageProduct", paging);
                        request.setAttribute("listProduct", listProduct);
                    } else {
                        request.setAttribute("message", "This Product Type haven't got any products yet");
                    }
                    request.setAttribute("listTopProduct", listTopProducts.subList(0, 3));
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
                    List<Products> listDateposted = productsFacade.showAll();
                    paging = new PageProduct(TechLineUtils.buildProductAdmin(listDateposted), 10);
                    String n3 = request.getParameter("btn");
                    if (n3 != null) {
                        if (n3.equals("next")) {
                            paging.next();
                        }
                        if (n3.equals("prev")) {
                            paging.prev();
                        }
                    }
                    String pages3 = request.getParameter("page");
                    if (pages3 != null) {
                        int m = Integer.parseInt(pages3);
                        paging.setPageIndex(m);
                        paging.updateModel();
                    }
                    request.setAttribute("pageProduct", paging);
                    request.getRequestDispatcher("admin/product.jsp").forward(request, response);
                    break;

                case "showBrand":
                    paging = new PageProduct(listBrands, 10);
                    String nBrand = request.getParameter("btn");
                    if (nBrand != null) {
                        if (nBrand.equals("next")) {
                            paging.next();
                        }
                        if (nBrand.equals("prev")) {
                            paging.prev();
                        }
                    }
                    String pagesBrand = request.getParameter("page");
                    if (pagesBrand != null) {
                        int m = Integer.parseInt(pagesBrand);
                        paging.setPageIndex(m);
                        paging.updateModel();
                    }
                    request.setAttribute("pageBrands", paging);
                    request.getRequestDispatcher("admin/brand.jsp").forward(request, response);
                    break;

                case "showCategories":
                    request.setAttribute("listCategories", listCategories);
                    request.getRequestDispatcher("admin/categories.jsp").forward(request, response);
                    break;

                case "showReport":
                    request.getRequestDispatcher("admin/report.jsp").forward(request, response);
                    break;

                case "showProductType":
                    listProductTypes = productTypesFacade.showAll();
                    paging = new PageProduct(listProductTypes, 10);
                    String nProductType = request.getParameter("btn");
                    if (nProductType != null) {
                        if (nProductType.equals("next")) {
                            paging.next();
                        }
                        if (nProductType.equals("prev")) {
                            paging.prev();
                        }
                    }
                    String pagesProductType = request.getParameter("page");
                    if (pagesProductType != null) {
                        int m = Integer.parseInt(pagesProductType);
                        paging.setPageIndex(m);
                        paging.updateModel();
                    }
                    request.setAttribute("pageProductType", paging);
                    request.getRequestDispatcher("admin/type.jsp").forward(request, response);
                    break;

                case "showOrder":

                    paging = new PageProduct(listOrderMaster, 15);
                    String nOrderMaster = request.getParameter("btn");
                    if (nOrderMaster != null) {
                        if (nOrderMaster.equals("next")) {
                            paging.next();
                        }
                        if (nOrderMaster.equals("prev")) {
                            paging.prev();
                        }
                    }
                    String pagesOrderMaster = request.getParameter("page");

                    if (pagesOrderMaster != null) {
                        int m = Integer.parseInt(pagesOrderMaster);
                        paging.setPageIndex(m);
                        paging.updateModel();
                    }

                    request.setAttribute("pageOrderMaster", paging);
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
                    response.setContentType("text/html;charset=UTF-8");
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(cal.YEAR);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String aftermonth = (year - 1) + "-12-01";
                    String beforemonth;
                    gson = new Gson();
                    int[] datasProduct = new int[12];
                    int[] datasOrder = new int[12];
                    try {
                        for (int i = 1; i < 12; i++) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(year);
                            sb.append("-");
                            sb.append(String.format("%02d", i));
                            sb.append("-01");
                            beforemonth = sb.toString();
                            datasProduct[i] = (int) productsFacade.countProductsByMonth(sdf.parse(aftermonth), sdf.parse(beforemonth));
                            datasOrder[i] = (int) orderMasterFacade.countOrderByMonth(sdf.parse(aftermonth), sdf.parse(beforemonth));
                            aftermonth = beforemonth;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ChartModel modelProducts = new ChartModel();
                    modelProducts.setName("Products");
                    modelProducts.setData(datasProduct);
                    ChartModel modelOrders = new ChartModel();
                    modelOrders.setName("Orders");
                    modelOrders.setData(datasOrder);
                    List<ChartModel> chart = new ArrayList<>();
                    chart.add(modelProducts);
                    chart.add(modelOrders);
                    String jsonChart = gson.toJson(chart);
                    System.out.println("Json " + jsonChart.toString());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(jsonChart);
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
                    String approvalDate = usersFacade.find(user.getUserId()).getSeller().getApprovedDate();
                    StringBuilder reFormatDate;
                    if (approvalDate.contains("/")) {
                        String[] splitDate = approvalDate.split("/");
                        reFormatDate = new StringBuilder();
                        reFormatDate.append(splitDate[2]).append("-");
                        reFormatDate.append(splitDate[1]).append("-");
                        reFormatDate.append(splitDate[0]);
                        request.setAttribute("approvalDate", reFormatDate.toString());
                    } else {
                        request.setAttribute("approvalDate", approvalDate);
                    }
                    request.setAttribute("user", usersFacade.find(user.getUserId()));
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
                    request.setAttribute("listBrands", listBrands.subList(0, 6));
                    request.getRequestDispatcher("customer.jsp").forward(request, response);
                    break;
                case "OrderHistory":
                    request.setAttribute("listOrderMasterCustomer", orderMasterFacade.getOrderByUserID(user.getUserId()));
                    request.setAttribute("listCategories", listCategories);
                    request.setAttribute("listBrands", listBrands.subList(0, 6));
                    request.getRequestDispatcher("customerOrder.jsp").forward(request, response);
                    break;
                case "sellerProduct":
                    List<Products> sellerProduct = productsFacade.getListProductBySeller(user.getUserId());
                    paging = new PageProduct(TechLineUtils.buildProductAdmin(sellerProduct), 10);
                    String n3S = request.getParameter("btn");
                    if (n3S != null) {
                        if (n3S.equals("next")) {
                            paging.next();
                        }
                        if (n3S.equals("prev")) {
                            paging.prev();
                        }
                    }
                    String pages3S = request.getParameter("page");
                    if (pages3S != null) {
                        int m = Integer.parseInt(pages3S);
                        paging.setPageIndex(m);
                        paging.updateModel();
                    }
                    request.setAttribute("pageProduct", paging);
                    request.getRequestDispatcher("seller/product.jsp").forward(request, response);
                    break;

                case "sellerOrder":
                    TechLineUtils util = new TechLineUtils();
                    List<Products> lsProducts = (List<Products>) user.getProductsCollection();
                    List<SellerOrder> sellerOrder = new ArrayList<>();
                    for (Products prd : lsProducts) {
                        for (OrderDetails detail : prd.getOrderDetailsCollection()) {
                            SellerOrder slOrder = new SellerOrder();
                            slOrder.setOrderId(detail.getOrderMaster().getOrderMId());
                            slOrder.setBuyer(detail.getOrderMaster().getUserId().getFullname());
                            slOrder.setDateOrdered(detail.getOrderMaster().getDateOrdered());
                            slOrder.setOrderNote(detail.getOrderMaster().getOrderNote());
                            slOrder.setOrderTotalPrice(detail.getOrderMaster().getOrderTotalPrice().toString());
                            slOrder.setOrderStatus(detail.getOrderMaster().getOrderStatus());
                            sellerOrder.add(slOrder);
                        }
                    }

                    paging = new PageProduct(sellerOrder, 15);
                    String nOrderMasterS = request.getParameter("btn");
                    if (nOrderMasterS != null) {
                        if (nOrderMasterS.equals("next")) {
                            paging.next();
                        }
                        if (nOrderMasterS.equals("prev")) {
                            paging.prev();
                        }
                    }
                    String pagesOrderMasterS = request.getParameter("page");

                    if (pagesOrderMasterS != null) {
                        int m = Integer.parseInt(pagesOrderMasterS);
                        paging.setPageIndex(m);
                        paging.updateModel();
                    }

//                     = util.getSellerOrdered(user.getUserId());
                    //Adapt to new model with JPQL and for loop
                    request.setAttribute("order", paging);
                    request.getRequestDispatcher("seller/order.jsp").forward(request, response);
                    break;

                case "sellerShowReport":
                    request.getRequestDispatcher("seller/report.jsp").forward(request, response);
                    break;
                case "sellerProductDetail":
                    request.setAttribute("listBrand", listBrands);
                    request.setAttribute("listType", productTypesFacade.showAll());
                    request.setAttribute("productDetail", productsFacade.find(request.getParameter("productId")));
                    String image = productsFacade.find(request.getParameter("productId")).getProductImage();
                    String[] splitImagePath = image.split(",");
                    request.setAttribute("txtImage", splitImagePath[0]);
                    request.setAttribute("txtSubImage1", splitImagePath[1]);
                    request.setAttribute("txtSubImage2", splitImagePath[2]);
                    request.setAttribute("txtSubImage3", splitImagePath[3]);
                    request.setAttribute("txtSubImage4", splitImagePath[4]);
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
                case "printReport":
                    String reportType = request.getParameter("txtReportType");
                    String limit = request.getParameter("txtLimit");
                    int limitOrder = Integer.parseInt(limit);
                    switch (reportType) {
                        case "topProductReport.jrxml":
                            if (!user.getUserId().equals("admin")) {
                                listProduct = productsFacade.getTopProduct(user.getUserId());
                            } else {
                                listProduct = productsFacade.getTopProduct();
                            }

                            List<TopProductStaticModel> listStatic = new ArrayList<>();

                            for (Products p : listProduct) {
                                TopProductStaticModel model = new TopProductStaticModel();
                                model.setId(p.getProductId());
                                model.setName(p.getProductName());
                                StringBuilder sb = new StringBuilder();
                                int sold = 0;
                                for (OrderDetails o : p.getOrderDetailsCollection()) {
                                    sb.append(o.getOrderMaster().getOrderMId());
                                    sb.append(",");
                                    sold += o.getQuantity();
                                }
                                model.setSold(sold);
                                model.setOrderid(sb.toString());
                                model.setSeller(p.getUserId().getFullname());
                                listStatic.add(model);
                            }
                            Collections.sort(listStatic, new Comparator<TopProductStaticModel>() {
                                @Override
                                public int compare(TopProductStaticModel o1, TopProductStaticModel o2) {
                                    return Integer.valueOf(o2.getSold()).compareTo(o1.getSold());
                                }
                            });
                            request.setAttribute("model", "topProductReport.jrxml");
                            if (limitOrder > listStatic.size()) {
                                limitOrder = listStatic.size();
                            }
                            request.setAttribute("products", listStatic.subList(0, limitOrder));
                            break;
                        case "sellerReport.jrxml":
                            List<SellerReportModel> listSellerReport = new ArrayList<>();

                            for (Seller s : sellerFacadeLocal.showAll()) {
                                List<OrderDetails> listDetails = new ArrayList<>();
                                SellerReportModel sReport = new SellerReportModel();
                                sReport.setId(s.getUserId());
                                sReport.setName(s.getUsers().getFullname());
                                sReport.setStorename(s.getStoreName());

                                for (Products p : s.getUsers().getProductsCollection()) {
                                    for (OrderDetails o : p.getOrderDetailsCollection()) {
                                        if (listDetails.contains(o)) {
                                            continue;
                                        }
                                        listDetails.add(o);
                                    }
                                }
                                sReport.setNumberorders(listDetails.size());
                                sReport.setNumberproducts(s.getUsers().getProductsCollection().size());
                                listSellerReport.add(sReport);
                            }
                            Collections.sort(listSellerReport, new Comparator<SellerReportModel>() {
                                @Override
                                public int compare(SellerReportModel o1, SellerReportModel o2) {
                                    return Integer.valueOf(o2.getNumberorders()).compareTo(o1.getNumberorders());
                                }
                            });

                            request.setAttribute("model", "sellerReport.jrxml");
                            if (limitOrder > listSellerReport.size()) {
                                limitOrder = listSellerReport.size();
                            }
                            request.setAttribute("products", listSellerReport.subList(0, limitOrder));
                            break;
                        case "customerReport.jrxml":
                            List<CustomerReportModel> listCustomerReport = new ArrayList<>();
                            for (Customers customerC : customersFacade.showAll()) {
                                CustomerReportModel cReport = new CustomerReportModel();
                                cReport.setId(customerC.getUsers().getUserId());
                                cReport.setName(customerC.getUsers().getFullname());
                                cReport.setNumberorders(customerC.getUsers().getOrderMasterCollection().size());
                                cReport.setNumbercomments(customerC.getUsers().getProductsCommentCollection().size());
                                cReport.setPhone(customerC.getUsers().getPhone());
                                listCustomerReport.add(cReport);
                            }
                            Collections.sort(listCustomerReport, new Comparator<CustomerReportModel>() {
                                @Override
                                public int compare(CustomerReportModel o1, CustomerReportModel o2) {
                                    return Integer.valueOf(o2.getNumberorders()).compareTo(o1.getNumberorders());
                                }
                            });
                            request.setAttribute("model", "customerReport.jrxml");
                            if (limitOrder > listCustomerReport.size()) {
                                limitOrder = listCustomerReport.size();
                            }
                            request.setAttribute("products", listCustomerReport.subList(0, limitOrder));
                            break;
                        case "orderReport.jrxml":
                            List<OrderReportModel> listOrderReportModels = new ArrayList<>();
                            for (OrderMaster o : orderMasterFacade.findAll()) {
                                StringBuilder sb = new StringBuilder();
                                OrderReportModel oReport = new OrderReportModel();
                                oReport.setId(o.getOrderMId());
                                oReport.setBuyer(o.getUserId().getFullname());
                                for (OrderDetails oDetails : o.getOrderDetailsCollection()) {
                                    sb.append(oDetails.getProducts().getProductId());
                                    sb.append(" | ");
                                }

                                oReport.setProducts(sb.toString());
                                oReport.setTotalprice(o.getOrderTotalPrice());
                                oReport.setStatus(o.getOrderStatus());
                                listOrderReportModels.add(oReport);
                            }
                            Collections.sort(listOrderReportModels, new Comparator<OrderReportModel>() {
                                @Override
                                public int compare(OrderReportModel o1, OrderReportModel o2) {
                                    return Double.valueOf(o2.getTotalprice()).compareTo(o1.getTotalprice());
                                }
                            });
                            request.setAttribute("model", "orderReport.jrxml");
                            if (limitOrder > listOrderReportModels.size()) {
                                limitOrder = listOrderReportModels.size();
                            }
                            request.setAttribute("products", listOrderReportModels.subList(0, limitOrder));
                            break;
                        default:
                            break;
                    }

                    request.setAttribute("action", "report 2");
                    request.getRequestDispatcher("report").forward(request, response);
                    break;
                case "sellerGetProductHistory":
                    String productId = request.getParameter("txtProductId");
                    gson = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
                    List<ProductsEditHistory> lsProductHistory = productsEditHistory.findByProductId(productId);
                    List<ProductHistory> lsConvert = new ArrayList<>();
                    SimpleDateFormat sdfSeller = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                    for(ProductsEditHistory data: lsProductHistory){
                        lsConvert.add(new ProductHistory(
                                String.valueOf(data.getProductsEditHistoryPK().getProductId()),
                                String.valueOf(data.getProductsEditHistoryPK().getVersion()),
                                String.valueOf(data.getProductName()), 
                                String.valueOf(data.getProductPrice()), 
                                String.valueOf(data.getProductDiscount()), 
                                String.valueOf(sdfSeller.format(data.getEditTime()))));
                    }
                    
                    String json = gson.toJson(lsConvert);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write(json);

                    break;

                default:
                    request.setAttribute("error", "Page not found");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
