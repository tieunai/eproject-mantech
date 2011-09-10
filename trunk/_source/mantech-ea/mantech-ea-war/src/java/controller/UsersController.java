package controller;

import entities.Roles;
import entities.Users;
import entities.Users_Roles;
import facades.RolesFacadeRemote;
import facades.Roles_UsersFacadeRemote;
import util.JsfUtil;
import util.PaginationHelper;
import facades.UsersFacadeRemote;
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

@ManagedBean(name = "usersController")
@SessionScoped
public class UsersController {

    @EJB
    private RolesFacadeRemote rolesFacade;
    @EJB
    private Roles_UsersFacadeRemote roles_UsersFacade;
    private Users current;
    private DataModel items = null;
    @EJB
    private facades.UsersFacadeRemote ejbFacade;
    private static facades.UsersFacadeRemote ejbFacade2;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String tmpPassword;
    private static int currentLoggedUserID = -1;
    private String tmpCurrentPassword;

    public String getTmpCurrentPassword() {
        return tmpCurrentPassword;
    }

    public void setTmpCurrentPassword(String tmpCurrentPassword) {
        this.tmpCurrentPassword = tmpCurrentPassword;
    }

    public static int getCurrentLoggedUserID() {
        return currentLoggedUserID;
    }

    public static Users getCurrentLoggedUser() {
        if (ejbFacade2 == null) {
            ejbFacade2 = lookupUsersFacadeRemote();
        }
        return ejbFacade2.find(currentLoggedUserID);
    }

    public Users getCurrentLoggedUserForW() {

        return getCurrentLoggedUser();
    }

    public static void setCurrentLoggedUserID(int currentLoggedUserID) {
        UsersController.currentLoggedUserID = currentLoggedUserID;
    }

    public static void setCurrentLoggedUserID(String userName) {
        if (ejbFacade2 == null) {
            ejbFacade2 = lookupUsersFacadeRemote();
        }
        Users u = ejbFacade2.findByUsername(userName);
        if (u != null) {
            UsersController.currentLoggedUserID = u.getUserID();
        }
    }

    public String getTmpPassword() {
        return tmpPassword;
    }

    public void setTmpPassword(String tmpPassword) {
        this.tmpPassword = tmpPassword;
    }

    public UsersController() {
        ejbFacade = lookupUsersFacadeRemote();
        roles_UsersFacade = lookupRUFacadeRemote();
        rolesFacade = lookupRolesFacadeRemote();
    }

    private static UsersFacadeRemote lookupUsersFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UsersFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/UsersFacade!facades.UsersFacadeRemote");
        } catch (NamingException ne) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RolesFacadeRemote lookupRolesFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RolesFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/RolesFacade!facades.RolesFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private Roles_UsersFacadeRemote lookupRUFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (Roles_UsersFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/Roles_UsersFacade!facades.Roles_UsersFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public int getUserIDByUserName(String userName) {
        Users u = getFacade().find(getSelected().getUsername());
        if (u != null) {
            return u.getUserID();
        }
        return -1;
    }

    public String userLogin() {
        Users u = getFacade().find(getSelected().getUsername());
        if (u != null) {
            if (u.getPassword().equals(getSelected().getPassword())) {
                return "/index.xhtml";
            }
        }
        return "/login.xhtml?do=try";
    }

    public Users getSelected() {
        if (current == null) {
            current = new Users();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsersFacadeRemote getFacade() {
        return ejbFacade;
    }

    private RolesFacadeRemote getRolesFacade() {
        return rolesFacade;
    }

    private Roles_UsersFacadeRemote getRUFacade() {
        return roles_UsersFacade;
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
        return "UsersList";
    }

    public String prepareView() {
        current = (Users) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "UsersView";
    }

    public String prepareCreate() {
        current = new Users();
        selectedItemIndex = -1;
        return "UsersCreate";
    }

    public String create() {
        try {
            if (getSelected().getPassword() == null || getSelected().getPassword().length() == 0) {
                throw new Exception("Password is not allow null");
            } else if (getSelected().getPassword().length() < 6) {
                throw new Exception("Length of password must great than 5 characters");
            } else if (!getTmpPassword().equals(getSelected().getPassword())) {
                throw new Exception("New Password and Confirm Password musth match!");
            }
            getSelected().setPassword(JsfUtil.hash(getSelected().getPassword()));
            getSelected().setIsOnline(false);
            getSelected().setLastVisit(new Date());
            getSelected().setCreateTime(new Date());
            getSelected().setCreateIP("192.168.1.1");
            getSelected().setIsEnable(true);

            getFacade().create(current);

            Users u = getFacade().find(getSelected().getUsername());
            if (u != null) {
                Roles roles = getRolesFacade().find(getSelected().getRoleID().getRoleID());
                if (roles != null) {
                    Users_Roles users_roles = new Users_Roles(roles.getRoleID(), u.getUserID());
                    getRUFacade().create(users_roles);
                }
            }

            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Users) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "UsersEdit";
    }

    public String prepareResetPassword() {
        current = (Users) getItems().getRowData();
        current.setPassword(null);

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "UsersResetPwd";
    }

    public String prepareUpdateProfile() {
        current = (Users) getFacade().find(currentLoggedUserID);
        return "/account/ProfileEdit";
    }

    public String prepareUpdatePassword() {
        current = (Users) getFacade().find(currentLoggedUserID);
        return "/account/ChangePwd";
    }

    public String update() {
        try {
            //getSelected().setPassword(JsfUtil.hash(getSelected().getPassword()));
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());
            getSelected().setIsOnline(false);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersUpdated"));
            return "UsersView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updatePassword() {
        try {
            if (getSelected().getPassword() == null || getSelected().getPassword().length() == 0) {
                throw new Exception("Password is not allow null");
            } else if (getSelected().getPassword().length() < 6) {
                throw new Exception("Length of password must great than 5 characters");
            } else if (!getTmpPassword().equals(getSelected().getPassword())) {
                throw new Exception("New Password and Confirm Password musth match!");
            }
            getSelected().setPassword(JsfUtil.hash(getSelected().getPassword()));
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersUpdated"));
            return "UsersView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updateLoggedUserPassword() {
        try {
            if (tmpCurrentPassword == null || tmpCurrentPassword.length() == 0) {
                throw new Exception("Current Password is not allow null.");
            } else if (tmpPassword == null || tmpPassword.length() == 0) {
                throw new Exception("New Password is not allow null.");
            } else if (getSelected().getPassword().length() < 6) {
                throw new Exception("Length of password must great than 5 characters.");
            } else if (!getTmpPassword().equals(getSelected().getPassword())) {
                throw new Exception("New Password and Confirm Password musth match.");
            } else if (!getSelected().getPassword().equals(tmpCurrentPassword)) {
                throw new Exception("Current password is not match.");
            }
            getSelected().setPassword(JsfUtil.hash(getSelected().getPassword()));
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersUpdated"));
            return "UsersView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Users) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "UsersList";
    }

    public String enable() {
        current = (Users) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performEnable();
        recreateModel();
        return "UsersList";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "UsersView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "UsersList";
        }
    }

    public String enableAndView() {
        performEnable();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "UsersView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "UsersList";
        }
    }

    private void performDestroy() {
        try {
            getSelected().setIsEnable(false);
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void performEnable() {
        try {
            getSelected().setIsEnable(true);
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersUpdated"));
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
        return "UsersList";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "UsersList";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getUsersSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Users.class)
    public static class UsersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsersController controller = (UsersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usersController");
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
            if (object instanceof Users) {
                Users o = (Users) object;
                return getStringKey(o.getUserID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UsersController.class.getName());
            }
        }
    }
}
