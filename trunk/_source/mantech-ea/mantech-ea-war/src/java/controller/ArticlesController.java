package controller;

import entities.Articles;
import util.JsfUtil;
import util.PaginationHelper;
import entities.Users;
import facades.ArticlesFacadeRemote;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

@ManagedBean (name="articlesController")
@SessionScoped
public class ArticlesController {

    private Articles current;
    private DataModel items = null;
    @EJB private facades.ArticlesFacadeRemote ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ArticlesController() {
        ejbFacade = lookupArticlesFacadeRemote();

    }

    private ArticlesFacadeRemote lookupArticlesFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ArticlesFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/ArticlesFacade!facades.ArticlesFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public Articles getSelected() {
        if (current == null) {
            current = new Articles();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ArticlesFacadeRemote getFacade() {
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

     public PaginationHelper getPaginationByEnable() {
   
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
        
                    //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                    return new ListDataModel(getFacade().findByEnable(true));
                }
            };
        
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "ArticlesList";
    }

    public String prepareView() {
        current = (Articles)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ArticlesView";
    }

    public String prepareCreate() {
        current = new Articles();
        selectedItemIndex = -1;
        return "ArticlesCreate";
    }

    public String create() {
        try {
            Users tmpUser = new Users();
            tmpUser.setUserID(2);

            getSelected().setCreateTime(new Date());
            getSelected().setCreateIP("192.168.1.1");
            getSelected().setUserID(tmpUser);
            getSelected().setDislikeCount(0);
            getSelected().setLikeCount(0);

            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArticlesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Articles)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ArticlesEdit";
    }

    public String update() {
        try {
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArticlesUpdated"));
            return "ArticlesView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Articles)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "ArticlesList";
    }

    public String enable() {
        current = (Articles)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performEnable();
        recreateModel();
        return "ArticlesList";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "ArticlesView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "ArticlesList";
        }
    }

    public String enableAndView() {
        performEnable();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "ArticlesView";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "ArticlesList";
        }
    }

    private void performDestroy() {
        try {
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());
            getSelected().setIsEnable(false);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArticlesUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void performEnable() {
        try {
            getSelected().setEditIP("192.168.1.1");
            getSelected().setEditTime(new Date());
            getSelected().setIsEnable(true);

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArticlesUpdated"));
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

     public DataModel getItemsByEnable() {
  
        items = null;

        items = getPaginationByEnable().createPageDataModel();

        return items;
    }
    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "ArticlesList";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "ArticlesList";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getTrueFalseAvailableSelectOne() {
        SelectItem[] trueFalseItems = new SelectItem[2];
        trueFalseItems[0] = new SelectItem("true", "True");
        trueFalseItems[1] = new SelectItem("false", "False");

        return trueFalseItems;
    }

    @FacesConverter(forClass=Articles.class)
    public static class ArticlesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ArticlesController controller = (ArticlesController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "articlesController");
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
            if (object instanceof Articles) {
                Articles o = (Articles) object;
                return getStringKey(o.getArticleID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+ArticlesController.class.getName());
            }
        }

    }

}
