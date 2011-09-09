package controller;

import entities.Complaints;
import util.JsfUtil;
import util.PaginationHelper;
import entities.Users;
import facades.ComplaintsFacadeRemote;
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

@ManagedBean(name = "complaintsController")
@SessionScoped
public class ComplaintsController {

    private Complaints current;
    private static Complaints currentToAnswer;
    private DataModel items = null;
    @EJB
    private facades.ComplaintsFacadeRemote ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ComplaintsController() {
        ejbFacade = lookupComplaintsFacadeRemote();
    }

    private ComplaintsFacadeRemote lookupComplaintsFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ComplaintsFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/ComplaintsFacade!facades.ComplaintsFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public Complaints getSelected() {
        if (current == null) {
            current = new Complaints();
            selectedItemIndex = -1;
        }
        return current;
    }

    public static Complaints getStaticSelected() {
        if (currentToAnswer == null) {
            currentToAnswer = new Complaints();
        }
        return currentToAnswer;
    }

    private ComplaintsFacadeRemote getFacade() {
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

    public PaginationHelper getPaginationByUserID() {

        pagination = new PaginationHelper(10) {

            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                Users u = UsersController.getCurrentLoggedUser();
                return new ListDataModel(getFacade().findByUserID(u));
            }
        };

        return pagination;
    }

    public PaginationHelper getPaginationByUserRef() {

        pagination = new PaginationHelper(10) {

            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                Users u = UsersController.getCurrentLoggedUser();
                return new ListDataModel(getFacade().findByUserRef(u));
            }
        };

        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "ComplaintsList";
    }

    public String prepareView() {
        current = (Complaints) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ComplaintsView";
    }

    public String prepareViewAnswers() {
        current = (Complaints) getItems().getRowData();
        currentToAnswer = null;
        currentToAnswer = current;

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "AnswersList";
    }

    public String prepareAnswer() {
        currentToAnswer = (Complaints) getItems().getRowData();
        current = (Complaints) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "AnswersCreate";
    }

    public String prepareCreate() {
        current = new Complaints();
        selectedItemIndex = -1;
        return "ComplaintsCreate";
    }

    public String create() {
        try {
            Users tmpUser = new Users();
            tmpUser.setUserID(2);

            getSelected().setUserID(tmpUser);
            getSelected().setCreateTime(new Date());
            getSelected().setCreateIP("192.168.1.1");
            getSelected().setIsEnable(true);
            getSelected().setIsFinished(false);
            getSelected().setHasReplied(false);
            getSelected().setPriority(getSelected().getPriority() == null ? 1 : getSelected().getPriority());

            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComplaintsCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Complaints) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ComplaintsEdit";
    }

    public String prepareAssign() {
        System.out.println("PREPARE ASSIGN");
        current = (Complaints) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ComplaintsAssign";
    }

    public String update() {
        try {
            Users tmpUser = new Users();
            tmpUser.setUserID(1);
            tmpUser.setUsername("demo");

            getSelected().setEditorID(tmpUser);
            getSelected().setEditIP("192.168.1.1");

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComplaintsUpdated"));
            return "ComplaintsView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Complaints) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "ComplaintsList";
    }

    public String enable() {
        current = (Complaints) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performEnable();
        recreateModel();
        return "ComplaintsList";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "ComplaintsView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "ComplaintsList";
        }
    }

    public String enableAndView() {
        performEnable();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "ComplaintsView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "ComplaintsList";
        }
    }

    private void performDestroy() {
        try {
            Users tmpUser = new Users();
            tmpUser.setUserID(1);
            tmpUser.setUsername("demo");

            getSelected().setEditorID(tmpUser);
            getSelected().setEditIP("192.168.1.1");
            getSelected().setIsEnable(false);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComplaintsUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void performEnable() {
        try {
            Users tmpUser = new Users();
            tmpUser.setUserID(1);
            tmpUser.setUsername("demo");

            getSelected().setEditorID(tmpUser);
            getSelected().setEditIP("192.168.1.1");
            getSelected().setIsEnable(true);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComplaintsUpdated"));
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
        //if (items == null) {
        items = getPagination().createPageDataModel();
        //}
        return items;
    }

    public DataModel getItemsByUserID() {
        //if (items == null) {
        items = null;
        // if (UsersController.getCurrentLoggedUserID() > 0) {
        items = getPaginationByUserID().createPageDataModel();
        //}
        //}
        return items;
    }

    public DataModel getItemsByUserRef() {
        //if (items == null) {
        items = null;
        //if (UsersController.getCurrentLoggedUserID() > 0) {
        items = getPaginationByUserRef().createPageDataModel();
        //}
        //}
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "ComplaintsList";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "ComplaintsList";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Complaints.class)
    public static class ComplaintsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ComplaintsController controller = (ComplaintsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "complaintsController");
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
            if (object instanceof Complaints) {
                Complaints o = (Complaints) object;
                return getStringKey(o.getComplaintID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ComplaintsController.class.getName());
            }
        }
    }
}
