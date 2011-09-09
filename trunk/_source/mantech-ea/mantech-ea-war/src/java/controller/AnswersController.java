package controller;

import entities.Answers;
import util.JsfUtil;
import util.PaginationHelper;
import entities.Users;
import facades.AnswersFacadeRemote;
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

@ManagedBean (name="answersController")
@SessionScoped
public class AnswersController {

    private Answers current;
    private DataModel items = null;
    @EJB private facades.AnswersFacadeRemote ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AnswersController() {
        ejbFacade = lookupAnswersFacadeRemote();
    }

    private AnswersFacadeRemote lookupAnswersFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (AnswersFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/AnswersFacade!facades.AnswersFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public Answers getSelected() {
        if (current == null) {
            current = new Answers();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AnswersFacadeRemote getFacade() {
        return ejbFacade;
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
                    //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                    return new ListDataModel(getFacade().findAll());
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "AnswersList";
    }

    public String prepareView() {
        current = (Answers)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "AnswersView";
    }

    public String prepareCreate() {
        current = new Answers();
        selectedItemIndex = -1;
        return "AnswersCreate";
    }

    public String create() {
        try {
            getSelected().setIsEnable(true);
            getSelected().setComplaintID(ComplaintsController.getStaticSelected());
            getSelected().setCreateIP("192.168.1.1");
            getSelected().setCreateTime(new Date());

            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnswersCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Answers)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "AnswersEdit";
    }

    public String update() {
        try {
            Users tmpUser = new Users();
            tmpUser.setUserID(1);
            tmpUser.setUsername("demo");

            getSelected().setEditorID(tmpUser);
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnswersUpdated"));
            return "AnswersView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Answers)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "AnswersList";
    }

    public String enable() {
        current = (Answers)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performEnable();
        recreateModel();
        return "AnswersList";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "AnswersView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "AnswersList";
        }
    }

    public String enableAndView() {
        performEnable();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "AnswersView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "AnswersList";
        }
    }

    private void performDestroy() {
        try {
            Users tmpUser = new Users();
            tmpUser.setUserID(1);
            tmpUser.setUsername("demo");

            getSelected().setEditorID(tmpUser);
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());
            getSelected().setIsEnable(false);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnswersUpdated"));
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
            getSelected().setEditTime(new Date());
            getSelected().setIsEnable(true);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnswersUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count-1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
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
        return "AnswersList";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "AnswersList";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass=Answers.class)
    public static class AnswersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AnswersController controller = (AnswersController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "answersController");
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
            if (object instanceof Answers) {
                Answers o = (Answers) object;
                return getStringKey(o.getAnswerID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+AnswersController.class.getName());
            }
        }

    }

}
