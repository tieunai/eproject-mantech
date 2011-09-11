package controller;

import entities.Departments;
import entities.Threads;
import facades.DepartmentsFacadeRemote;
import util.JsfUtil;
import util.PaginationHelper;
import facades.ThreadsFacadeRemote;
import java.io.Serializable;
import java.util.Date;

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

@ManagedBean(name = "threadsController")
@SessionScoped
public class ThreadsController implements Serializable{

    private Threads current;
    private DataModel items = null;
    @EJB
    private facades.ThreadsFacadeRemote ejbFacade;
    @EJB
    private facades.DepartmentsFacadeRemote departmentFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ThreadsController() {
        ejbFacade = lookupThreadsFacadeRemote();
        departmentFacade = lookupDepartmentsFacadeRemote();
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

    private ThreadsFacadeRemote lookupThreadsFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ThreadsFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/ThreadsFacade!facades.ThreadsFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public Threads getSelected() {
        if (current == null) {
            current = new Threads();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ThreadsFacadeRemote getFacade() {
        return ejbFacade;
    }

    private DepartmentsFacadeRemote getDepartmentFacade() {
        return departmentFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "ThreadsList";
    }

    public String prepareView() {
        current = (Threads) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ThreadsView";
    }

    public String prepareCreate() {
        current = new Threads();
        selectedItemIndex = -1;
        return "ThreadsCreate";
    }

    public String create() {
        try {
            getSelected().setCreateTime(new Date());
            getSelected().setCreateIP("192.168.1.1");
            getSelected().setIsEnable(true);

            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ThreadsCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Threads) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ThreadsEdit";
    }

    public String update() {
        try {
            getSelected().setEditTime(new Date());
            getSelected().setEditIP("192.168.1.1");

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ThreadsUpdated"));
            return "ThreadsView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Threads) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "ThreadsList";
    }

    public String enable() {
        current = (Threads) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performEnable();
        recreateModel();
        return "ThreadsList";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "ThreadsView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "ThreadsList";
        }
    }

    public String enableAndView() {
        performEnable();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "ThreadsView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "ThreadsList";
        }
    }

    private void performDestroy() {
        try {
            getSelected().setEditTime(new Date());
            getSelected().setEditIP("192.168.1.1");
            getSelected().setIsEnable(false);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ThreadsUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void performEnable() {
        try {
            getSelected().setEditTime(new Date());
            getSelected().setEditIP("192.168.1.1");
            getSelected().setIsEnable(true);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ThreadsUpdated"));
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
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "ThreadsList";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "ThreadsList";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        if (ComplaintsController.getStaticCurrentDepartmentID() != -1) {
            Departments d = getDepartmentFacade().find(ComplaintsController.getStaticCurrentDepartmentID());
            if (d != null) {
                return JsfUtil.getThreadsSelectItems(ejbFacade.findByDepartment(d), true);
            }
        }
        return JsfUtil.getThreadsSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getTrueFalseAvailableSelectOne() {
        SelectItem[] trueFalseItems = new SelectItem[2];
        trueFalseItems[0] = new SelectItem("true", "True");
        trueFalseItems[1] = new SelectItem("false", "False");

        return trueFalseItems;
    }

    @FacesConverter(forClass = Threads.class)
    public static class ThreadsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ThreadsController controller = (ThreadsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "threadsController");
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
            if (object instanceof Threads) {
                Threads o = (Threads) object;
                return getStringKey(o.getThreadID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ThreadsController.class.getName());
            }
        }
    }
}
