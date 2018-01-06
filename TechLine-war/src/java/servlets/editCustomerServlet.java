/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Customers;
import entities.CustomersFacadeLocal;
import entities.ProductsComment;
import entities.ProductsCommentFacadeLocal;
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

/**
 *
 * @author nth15
 */
public class editCustomerServlet extends HttpServlet {
    @EJB
    private ProductsCommentFacadeLocal productsCommentFacade;

    @EJB
    private CustomersFacadeLocal customersFacade;
    @EJB
    private UsersFacadeLocal usersFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            Users user = (Users) request.getSession().getAttribute("user");
            switch (action) {
                case "editProfileCustomer":
                    Customers customer = user.getCustomers();
                    user.setFullname(request.getParameter("txtName"));
                    user.setEmail(request.getParameter("txtEmail"));
                    user.setPhone(request.getParameter("txtPhone"));
                    usersFacade.edit(user);
                    String birthday = request.getParameter("ddlDay") + "/" + request.getParameter("ddlMonth") + "/" + request.getParameter("ddlYear");
                    customer.setAddress(request.getParameter("txtAddress"));
                    customer.setGender(request.getParameter("gender"));
                    customer.setDob(birthday);
                    customersFacade.edit(customer);//completed edit customer
                    //re-display birthday
                    String disBirthday[] = customer.getDob().split("/");
                    request.setAttribute("date", Integer.parseInt(disBirthday[0]));
                    request.setAttribute("month", Integer.parseInt(disBirthday[1]));
                    request.setAttribute("year", Integer.parseInt(disBirthday[2]));
                    List<Integer> listDate = new ArrayList<>();
                    List< Integer> listMonth = new ArrayList<>();
                    List< Integer> listYear = new ArrayList<>();
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
                    request.setAttribute("customer", customer);
                    request.setAttribute("myMess", "Edit successful!");
                    request.getRequestDispatcher("customer.jsp").forward(request, response);
                    break;
                case "cusChangePassword":
                    String inputPass = request.getParameter("txtOldPassword");
                    if (!inputPass.equals(user.getPassword())) {
                        request.setAttribute("loginError", "Incorrect password!");
                        request.getRequestDispatcher("customer.jsp").forward(request, response);
                        break;
                    }
                    user.setPassword(request.getParameter("txtNewPass"));
                    usersFacade.edit(user);
                    request.setAttribute("loginError", "Change password successfully!");
                    request.setAttribute("user", usersFacade.find(user.getUserId()));
                    request.setAttribute("customer", customersFacade.find(user.getUserId()));
                    String birthday2[] = customersFacade.find(user.getUserId()).getDob().split("/");
                    request.setAttribute("date", Integer.parseInt(birthday2[0]));
                    request.setAttribute("month", Integer.parseInt(birthday2[1]));
                    request.setAttribute("year", Integer.parseInt(birthday2[2]));
                    List<Integer> listDate1 = new ArrayList<>();
                    List<Integer> listMonth1 = new ArrayList<>();
                    List<Integer> listYear1 = new ArrayList<>();
                    for(int i = 1; i < 32; i++) {
                        listDate1.add(i);
                        if(i < 13)
                            listMonth1.add(i);
                    }
                    for(int i = 1950; i < 2018; i++) {
                        listYear1.add(i);
                    }
                    request.setAttribute("listDate", listDate1);
                    request.setAttribute("listMonth", listMonth1);
                    request.setAttribute("listYear", listYear1);
                    request.getRequestDispatcher("customer.jsp").forward(request, response);
                    break;
                case "blockCustomer":
                    String[] cusIdBlock = request.getParameterValues("cbkCusID");
                    for(String sCus: cusIdBlock) {
                        Users uBlock = usersFacade.find(sCus);
                        //Block customer comments
                        List<ProductsComment> listCm= (List<ProductsComment>) uBlock.getProductsCommentCollection();
                        for(ProductsComment pc: listCm) {
                            pc.setCommentStatus(Boolean.FALSE);
                            productsCommentFacade.edit(pc);
                        }
                        //Block customer
                        uBlock.setUserStatus(Boolean.FALSE);
                        usersFacade.edit(uBlock);
                    }
                    request.setAttribute("myMessCus", "Blocked customers successfully!");
                    request.getRequestDispatcher("viewServlet?action=showUser").forward(request, response);
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
