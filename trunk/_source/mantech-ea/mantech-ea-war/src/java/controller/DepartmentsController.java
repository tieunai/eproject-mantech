package controller;

import entities.Departments;
import util.JsfUtil;
import util.PaginationHelper;
import facades.DepartmentsFacadeRemote;
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
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.IReport;

@ManagedBean(name = "departmentsController")
@SessionScoped
public class DepartmentsController {

    private Departments current;
    private DataModel items = null;
    @EJB
    private facades.DepartmentsFacadeRemote ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DepartmentsController() {
        ejbFacade = lookupDepartmentsFacadeRemote();
    }
    public void reportDepartment(){
        try {
            List<entities.Departments> list = new ArrayList<entities.Departments>();
            list = getFacade().findAll();
            Map parameters = new HashMap();
            parameters.put("REPORT_TIME", new Date());
            IReport.report("1department2.jrxml", "PDF", list,parameters);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Report create successful!"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    private DepartmentsFacadeRemote lookupDepartmentsFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DepartmentsFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/DepartmentsFacade!facades.DepartmentsFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public Departments getSelected() {
        if (current == null) {
            current = new Departments();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DepartmentsFacadeRemote getFacade() {
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
                //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                return new ListDataModel(getFacade().findAll());
            }
        };

        return pagination;
    }

    public PaginationHelper getPaginationForClient() {

        pagination = new PaginationHelper(10) {

            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                return new ListDataModel(getFacade().emplFindAll());
            }
        };

        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "DepartmentsList";
    }

    public String prepareView() {
        current = (Departments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "DepartmentsView";
    }

    public String prepareCreate() {
        current = new Departments();
        selectedItemIndex = -1;
        return "DepartmentsCreate";
    }

    public String create() {
        try {
            getSelected().setIsEnable(true);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartmentsCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Departments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "DepartmentsEdit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartmentsUpdated"));
            return "DepartmentsView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Departments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "DepartmentsList";
    }

    public String enable() {
        current = (Departments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performEnable();
        recreateModel();
        return "DepartmentsList";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "DepartmentsView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "DepartmentsList";
        }
    }

    public String enableAndView() {
        performEnable();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "DepartmentsView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "DepartmentsList";
        }
    }

    private void performDestroy() {
        try {
            getSelected().setIsEnable(false);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartmentsUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void performEnable() {
        try {
            getSelected().setIsEnable(true);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartmentsUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {

        items = getPagination().createPageDataModel();

        return items;
    }

    public DataModel getItemsForClient() {

        items = getPaginationForClient().createPageDataModel();

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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getDepartmentsSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Departments.class)
    public static class DepartmentsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DepartmentsController controller = (DepartmentsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "departmentsController");
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
            if (object instanceof Departments) {
                Departments o = (Departments) object;
                return getStringKey(o.getDepartmentID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DepartmentsController.class.getName());
            }
        }
    }
}
