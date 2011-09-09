/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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

    /** Creates a new instance of LoginController */
    public LoginController() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    private String username;
    private String password;

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

                message = "Username : " + principal.getName() + " You are an Administrator, you can really f**k things up now";
                return "admin";
            } else if (request.isUserInRole("employee")) {
                message = "Username : " + principal.getName() + " You are only a Employee, Don't you have a Spreadsheet to be working on??";
                return "employee";
            } else if (request.isUserInRole("technician")) {
                message = "Username : " + principal.getName() + " You're wasting my resources...";
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

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/Login.xhtml");
    }
}
