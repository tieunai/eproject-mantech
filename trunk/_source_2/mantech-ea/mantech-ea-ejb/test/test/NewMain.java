/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import facades.AnswersFacadeRemote;
import facades.ArticlesFacadeRemote;
import facades.ComplaintsFacadeRemote;
import facades.DepartmentsFacadeRemote;
import facades.FAQsFacadeRemote;
import facades.RolesFacadeRemote;
import facades.ThreadsFacadeRemote;
import facades.UsersFacadeRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author HP
 */
public class NewMain {
    UsersFacadeRemote usersFacade = lookupUsersFacadeRemote();
    ThreadsFacadeRemote threadsFacade = lookupThreadsFacadeRemote();
    FAQsFacadeRemote fAQsFacade = lookupFAQsFacadeRemote();
    DepartmentsFacadeRemote departmentsFacade = lookupDepartmentsFacadeRemote();
    ComplaintsFacadeRemote complaintsFacade = lookupComplaintsFacadeRemote();
    ArticlesFacadeRemote articlesFacade = lookupArticlesFacadeRemote();
    AnswersFacadeRemote answersFacade = lookupAnswersFacadeRemote();
    RolesFacadeRemote rolesFacade = lookupRolesFacadeRemote();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
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

    private AnswersFacadeRemote lookupAnswersFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (AnswersFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/AnswersFacade!facades.AnswersFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    private ComplaintsFacadeRemote lookupComplaintsFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ComplaintsFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/ComplaintsFacade!facades.ComplaintsFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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

    private FAQsFacadeRemote lookupFAQsFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (FAQsFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/FAQsFacade!facades.FAQsFacadeRemote");
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

    private UsersFacadeRemote lookupUsersFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UsersFacadeRemote) c.lookup("java:global/mantech-ea/mantech-ea-ejb/UsersFacade!facades.UsersFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
