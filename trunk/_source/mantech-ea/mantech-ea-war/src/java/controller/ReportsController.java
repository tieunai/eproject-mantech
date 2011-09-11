/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Departments;
import entities.VcomplaintsReport;
import facades.VComplaintsReportFacadeRemote;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.IReport;
import util.JsfUtil;
import util.PaginationHelper;

/**
 *
 * @author PoPEYE
 */
@ManagedBean(name = "reportsController")
@SessionScoped
public class ReportsController {

    private VcomplaintsReport current;
    private DataModel items = null;
    @EJB
    private facades.VComplaintsReportFacadeRemote ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String fdate;
    private String tdate;

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }
    private SimpleDateFormat sdf = null;
    private static String currentDepartmentID = "-1";
    private static String currentThreadID = "-1";
    private static String currentUserRef = "-1";

    public String getCurrentUserRef() {
        return currentUserRef;
    }

    public static String getStaticCurrentUserRef() {
        return currentUserRef;
    }

    public void setCurrentUserRef(String currentUserRef) {
        ReportsController.currentUserRef = currentUserRef;
    }

    public String getCurrentThreadID() {
        return currentThreadID;
    }

    public String getStaticCurrentThreadID() {
        return currentThreadID;
    }

    public void setCurrentThreadID(String currentThreadID) {
        ReportsController.currentThreadID = currentThreadID;
    }

    public static String getStaticCurrentDepartmentID() {
        return currentDepartmentID;
    }

    public String getCurrentDepartmentID() {
        return currentDepartmentID;
    }

    public void setCurrentDepartmentID(String currentDepartmentID) {
        ReportsController.currentDepartmentID = currentDepartmentID;
    }

    public void change(ValueChangeEvent event) {
        this.setCurrentDepartmentID((String) event.getNewValue());
    }

    /** Creates a new instance of ReportsController */
    public ReportsController() {
        ejbFacade = lookupVComplaintsReportFacadeRemote();
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        tdate = sdf.format(new Date());
    }

    private VComplaintsReportFacadeRemote lookupVComplaintsReportFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (VComplaintsReportFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/VComplaintsReportFacade!facades.VComplaintsReportFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public void reportComplaint() {
        sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date fd = new Date();
        Date td = new Date();
        if (fdate == null) {
            fdate = "01/01/2011";
        }
        String strFDate = fdate + " 00:00:00";
        String strTDate = tdate + " 23:59:59";
        String fileName = "";
        try {
            fd = sdf.parse(strFDate);
            td = sdf.parse(strTDate);
            List<entities.VcomplaintsReport> list = new ArrayList<entities.VcomplaintsReport>();
            list = getFacade().findBetweenTime(fd, td);
            if (currentDepartmentID == null && currentThreadID == null && currentUserRef == null) {
                list = getFacade().findBetweenTime(fd, td);
            } else if (currentDepartmentID != null && currentThreadID == null && currentUserRef == null) {
                list = getFacade().findAll(Integer.parseInt(currentDepartmentID), fd, td);
                fileName = "1department1.jrxml";
            } else if (currentDepartmentID == null && currentThreadID != null && currentUserRef == null) {
                list = getFacade().findBetweenTime(Integer.parseInt(currentThreadID), fd, td);
                fileName = "1thread1.jrxml";
            } else if (currentDepartmentID != null && currentThreadID != null && currentUserRef == null) {
                list = getFacade().findBetweenTime(Integer.parseInt(currentDepartmentID), Integer.parseInt(currentThreadID), fd, td);
                fileName = "1department1.jrxml";
            } else if (currentDepartmentID == null && currentThreadID == null && currentUserRef != null) {
                list = getFacade().findBetweenTime(Integer.parseInt(currentUserRef), fd, td);
                fileName = "1technician1.jrxml";
            } else if (currentDepartmentID != null && currentThreadID != null && currentUserRef != null) {

            }
            Map parameters = new HashMap();
            parameters.put("REPORT_TIME", new Date());
            IReport.report(fileName, "PDF", list, parameters);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Report create successful!"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void reportDetail() {
        try {
            List<entities.VcomplaintsReport> list = new ArrayList<entities.VcomplaintsReport>();
            list.add(getFacade().find(current.getComplaintID()));
            Map parameters = new HashMap();
            Date date = new Date();
            parameters.put("REPORT_TIME", date);
            IReport.report("1detail1.jrxml", "PDF", list, parameters);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Report create successful!"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public VcomplaintsReport getSelected() {
        if (current == null) {
            current = new VcomplaintsReport();
            selectedItemIndex = -1;
        }
        return current;
    }

    private VComplaintsReportFacadeRemote getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {

        pagination = new PaginationHelper(10) {

            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                return new ListDataModel(getFacade().findAll());
            }
        };

        return pagination;
    }

    public DataModel getItems() {

        items = getPagination().createPageDataModel();

        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "DepartmentsList";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "DepartmentsList";
    }

    @FacesConverter(forClass = VcomplaintsReport.class)
    public static class DepartmentsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReportsController controller = (ReportsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reportsController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof VcomplaintsReport) {
                VcomplaintsReport o = (VcomplaintsReport) object;
                return getStringKey(o.getDepartmentID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + VcomplaintsReport.class.getName());
            }
        }
    }
}
