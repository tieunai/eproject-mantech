/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.VcomplaintsReport;
import facades.VComplaintsReportFacadeRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

    /** Creates a new instance of ReportsController */
    public ReportsController() {
         ejbFacade = lookupVComplaintsReportFacadeRemote();
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
