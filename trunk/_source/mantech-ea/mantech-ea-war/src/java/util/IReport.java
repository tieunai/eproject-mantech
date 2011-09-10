/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author PoPEYE
 */
public class IReport {

    public static void report(String reportFile, String format, List<?> list, Map parameters) throws IOException, JRException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        ExternalContext ec = context.getExternalContext();
        String path = ec.getRealPath("/reports/" + reportFile);
        String outFile = reportFile.substring(0, reportFile.lastIndexOf("."));
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);

        ServletOutputStream servletOutputStream = response.getOutputStream();
        response.addHeader("Content-Disposition",
                "attachment;filename=" + outFile + "." + format);

        if ("PDF".equals(format)) {
            response.setContentType("application/pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
            exporter.exportReport();
        }
        if ("XLS".equals(format)) {
            response.setContentType("application/vnd.ms-excel");

            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,servletOutputStream);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_IMAGE_BORDER_FIX_ENABLED,Boolean.TRUE);

            exporter.exportReport();
        }
        servletOutputStream.close();
    }
}
