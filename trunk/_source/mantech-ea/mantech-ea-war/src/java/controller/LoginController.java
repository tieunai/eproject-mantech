/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.security.Principal;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nam Eco
 */
@ManagedBean (name="loginController")
@RequestScoped @Default
public class LoginController {

     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    /** Creates a new instance of LoginController */
    public LoginController() {
       
        if (session != null) {
            session.invalidate();
        }
    }
    private String username;
    private String password;
    private String rolename;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        String message = "";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {

            //Login via the Servlet Context
            request.login(username, password);


            //Retrieve the Principal
            Principal principal = request.getUserPrincipal();
            UsersController.setCurrentLoggedUserID(username);
            //Display a message based on the User role
            if (request.isUserInRole("admin")) {
                rolename =null;
                this.setRolename("admin");
                return "admin";
            } else if (request.isUserInRole("employee")) {
                rolename =null;
                this.setRolename("employee");
                return "employee";
            } else if (request.isUserInRole("technician")) {
                rolename =null;
                this.setRolename("technician");
                return "technician";
            }

            //Add the welcome message to the faces context
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
            return "success";
        } catch (ServletException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An Error Occured: Login failed", null));
            e.printStackTrace();
        }
        return "failure";
    }

    public void logout() throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/Login.xhtml");
        FacesContext.getCurrentInstance().getExternalContext().redirect("Login.xhtml");
    }
}
