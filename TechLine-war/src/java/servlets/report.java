/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import utils.ReportConnection;


public class report extends HttpServlet {



   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        generateReport();
        response.setContentType("text/html;charset=UTF-8");
        int BUFF_SIZE = 1024;
        byte[] buffer = new byte[BUFF_SIZE];
        response.setContentType("application/pdf");
        response.setHeader("Content-Type", "application/pdf");
        //report-- Change this path.
        String reportpath = "E:\\Projects\\TechLine-war\\web\\report\\adminReportUser2.pdf";
        File filePDF = new File(reportpath);
        FileInputStream fis = new FileInputStream(filePDF);     
        OutputStream os = response.getOutputStream();
        try
        {
            response.setContentLength((int) filePDF.length());
            int byteRead = 0;
            while ((byteRead = fis.read()) != -1)
            {
                os.write(byteRead);
            }
            os.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            os.close();
            fis.close();
        }
    } 
    
    
    protected void generateReport() {
        ReportConnection myConnection = new ReportConnection();
        Connection connection = null;
        try {
        String reportPath = "E:\\Projects\\TechLine-war\\web\\adminReportUser2.jrxml";
        JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        //Data Source
        connection = myConnection.connect();
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
        
        //outPut
        String outputPath = "E:/Projects/TechLine-war/web/report";
        File outDir = new File(outputPath);
        outDir.mkdirs();
        
        //export to PDF
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath+"/adminReportUser2.pdf");
        
        System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
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