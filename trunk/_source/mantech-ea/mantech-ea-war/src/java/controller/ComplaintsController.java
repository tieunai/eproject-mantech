package controller;

import entities.Complaints;
import util.JsfUtil;
import util.PaginationHelper;
import entities.Users;
import facades.ComplaintsFacadeRemote;
import java.io.Serializable;
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
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.mail.internet.MailDateFormat;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.IPAddressUtil;
import util.IReport;

@ManagedBean(name = "complaintsController")
@SessionScoped
public class ComplaintsController implements Serializable{

    private Complaints current;
    private DataModel items = null;
    @EJB
    private facades.ComplaintsFacadeRemote ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private static Complaints currentToAnswer;
    private String fdate;
    private String tdate;
    private static int currentDepartmentID = -1;
    private SimpleDateFormat sdf =null;
    public static int getStaticCurrentDepartmentID(){
        return currentDepartmentID;
    }

    public int getCurrentDepartmentID() {
        return currentDepartmentID;
    }

    public void setCurrentDepartmentID(int currentDepartmentID) {
        ComplaintsController.currentDepartmentID = currentDepartmentID;
    }

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

    public ComplaintsController() {
        ejbFacade = lookupComplaintsFacadeRemote();
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        tdate = sdf.format(new Date());
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

    public void reportComplaint() {
        try {
            List<entities.Complaints> list = new ArrayList<entities.Complaints>();
            list = getFacade().findAll();
            Map parameters = new HashMap();
            sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date fd = new Date();
            Date td = new Date();
            if(fdate ==null){fdate ="01/01/2011";}
            String strFDate = fdate +" 00:00:00";
            String strTDate = tdate +" 23:59:59";
            fd = sdf.parse(strFDate);
            td = sdf.parse(strTDate);
            list = getFacade().findBetweenTime(fd, td);
            if(getSelected().getUserRef() !=null){
                list = getFacade().findBetweenTime(getSelected().getThreadID(), fd, td);
            }
            parameters.put("REPORT_TIME", new Date());
            IReport.report("1complaint1.jrxml", "PDF", list,parameters);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Report create successful!"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    public void reportDetail() {
        try {
            List<entities.Complaints> list = new ArrayList<entities.Complaints>();
            list.add(getFacade().find(current.getComplaintID()));
            Map parameters = new HashMap();
            Date date = new Date();
            parameters.put("REPORT_TIME", date);
            IReport.report("1complaint2.jrxml", "PDF", list,parameters);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Report create successful!"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
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
                return new ListDataModel(getFacade().emplFindByUserID(u));
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
                return new ListDataModel(getFacade().emplFindByUserRef(u));
            }
        };

        return pagination;
    }

    public String listUserInDepartment(){
        System.out.println("DEPARTMENT: " + currentDepartmentID);
        return null;
    }

    public String prepareList() {
        recreateModel();
        return "ComplaintsList";
    }

    public String prepareView() {
        current = (Complaints) getCurrentItems().getRowData();

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ComplaintsView";
    }

    public String prepareTechView() {
        current = (Complaints) getCurrentItems().getRowData();

        setCompIsRead();

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ComplaintsView";
    }

    public String adminPrepareView() {
        current = (Complaints) getCurrentItems().getRowData();

        setCompIsAdminRead();

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ComplaintsView";
    }

    private void setCompIsRead(){
        try {
            getSelected().setIsRead(true);

            getFacade().edit(current);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void setCompIsAdminRead(){
        try {
            getSelected().setIsAdminRead(true);

            getFacade().edit(current);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String prepareViewAnswers() {
        current = (Complaints) getCurrentItems().getRowData();
        currentToAnswer = null;
        currentToAnswer = current;

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "AnswersList";
    }

    public String prepareUpdateCompToFinished() {
        current = (Complaints) getCurrentItems().getRowData();

        updateCompToFinished();
        return "ComplaintsList";
    }

    public String prepareAnswer() {
        currentToAnswer = (Complaints) getCurrentItems().getRowData();
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
            getSelected().setUserID(UsersController.getCurrentLoggedUser());
            getSelected().setCreateTime(new Date());
            getSelected().setCreateIP(IPAddressUtil.getClientIP());
            getSelected().setIsEnable(true);
            getSelected().setIsFinished(false);
            getSelected().setHasReplied(false);
            getSelected().setPriority(getSelected().getPriority() == null ? 1 : getSelected().getPriority());
            getSelected().setIsComplaintFinished(false);
            getSelected().setIsRead(false);
            getSelected().setIsAdminRead(false);

            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComplaintsCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Complaints) getCurrentItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ComplaintsEdit";
    }

    public String prepareAssign() {
        current = (Complaints) getCurrentItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ComplaintsAssign";
    }

    public String update() {
        try {
            getSelected().setEditorID(UsersController.getCurrentLoggedUser());
            getSelected().setEditIP(IPAddressUtil.getClientIP());

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComplaintsUpdated"));
            return "ComplaintsView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updateCompToFinished() {
        try {
            getSelected().setIsComplaintFinished(true);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComplaintsUpdated"));
            return "ComplaintsView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Complaints) getCurrentItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "ComplaintsList";
    }

    public String enable() {
        current = (Complaints) getCurrentItems().getRowData();
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
            getSelected().setEditorID(UsersController.getCurrentLoggedUser());
            getSelected().setEditIP(IPAddressUtil.getClientIP());
            getSelected().setIsEnable(false);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ComplaintsUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void performEnable() {
        try {
            getSelected().setEditorID(UsersController.getCurrentLoggedUser());
            getSelected().setEditIP(IPAddressUtil.getClientIP());
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

    public DataModel getCurrentItems() {
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

    public SelectItem[] getPriorityLevelSelectOne() {
        SelectItem[] priorityLevel = new SelectItem[3];
        priorityLevel[0] = new SelectItem(1, "Low");
        priorityLevel[1] = new SelectItem(2, "Medium");
        priorityLevel[2] = new SelectItem(3, "High");

        return priorityLevel;
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
