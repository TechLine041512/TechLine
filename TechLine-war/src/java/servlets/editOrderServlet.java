/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.OrderMaster;
import entities.OrderMasterFacadeLocal;

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
public class editOrderServlet extends HttpServlet {

    @EJB
    private OrderMasterFacadeLocal orderMasterFacade;

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
            String orderId = request.getParameter("oid");
            String json = "";
            OrderMaster order = orderMasterFacade.find(orderId);
            if (action.equals("countDeliveryFee")) {
                int fee = 0;
                int distance = (int) Double.parseDouble(request.getParameter("distance"));
                for (int i = 1; i <= distance; i++) {
                    if (i < 12) {
                        fee += 2;
                    } else {
                        fee += 3;
                    }
                }
                json = "{\"fee\":\"" + fee + "\"}";
            } else if (action.equals("changeOrder")) {
                String status = request.getParameter("status");
                String newStatus = "";
                switch (status) {
                    case "Processing":
                        newStatus = "Delivery";
                        break;
                    case "Delivery":
                        newStatus = "Done";
                        break;
                    default:
                        break;
                }
                order.setOrderStatus(newStatus);
                orderMasterFacade.edit(order);
                request.getRequestDispatcher("viewServlet?action=showOrder").forward(request, response);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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
        int fee = 0;
        int distance = (int) Double.parseDouble(request.getParameter("distance"));
        for (int i = 1; i <= distance; i++) {
            if (i < 12) {
                fee += 2;
            } else {
                fee += 3;
            }
        }
        String json = new Gson().toJson(fee);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        request.getRequestDispatcher("googleMap.jsp").forward(request, response);
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
