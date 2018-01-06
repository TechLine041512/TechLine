/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Constants;
import utils.TechLineUtils;

public class report extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Constants constant = new Constants();
        TechLineUtils util = new TechLineUtils();
        
        String action = request.getParameter("action");
        String reportTemplateName = request.getParameter("reportName");
        String reportPdfName = request.getParameter("reportName");
        String prjPath = util.getPathByProject(request, response);

        String reportTemplate = constant.REPORT_TEMPLATE;
        String reportPdf = constant.REPORT_PDF;
        switch (action) {
            case "report 2":
                //Generate Pdf report
                util.generatePdfReport(prjPath + reportTemplate, reportTemplateName, prjPath + reportPdf, reportPdfName);
                
                //read Pdf
                String reportPath = prjPath + reportPdf + reportPdfName;
                readReportPdf(request, response, reportPath);
                break;
        }
        
    }

    protected void readReportPdf(HttpServletRequest request, HttpServletResponse response, String pdfPath) throws FileNotFoundException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int BUFF_SIZE = 1024;
        byte[] buffer = new byte[BUFF_SIZE];
        response.setContentType("application/pdf");
        response.setHeader("Content-Type", "application/pdf");

        //report-- Change this path.
        String reportpath = pdfPath + ".pdf";
        File filePDF = new File(reportpath);
        FileInputStream fis = new FileInputStream(filePDF);
        OutputStream os = response.getOutputStream();
        try {
            response.setContentLength((int) filePDF.length());
            int byteRead = 0;
            while ((byteRead = fis.read()) != -1) {
                os.write(byteRead);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            os.close();
            fis.close();
        }

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
