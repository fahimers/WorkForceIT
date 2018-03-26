package view;

import model.Account;
import controller.Controller;
import model.accountInterface;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 * Detta är bean classen AcctManger som communserar med jsf i xhtml och med controllern
 * Använder sig av conversationScoped, därför måste det implamentera serializable i classen  
 * @author Elias
 */

@Named("acct") 
@ConversationScoped //spara
public class AcctManager implements Serializable {

    private static final long serialVersionUID = 16247164405L;
    
    @EJB // inject controller om inte har den blir null pointer exeption
    private Controller controller;
    
    private String username;
    private String password;
    private Exception transactionFailure;
    private boolean succes = false;
    private accountInterface account;
    private boolean admin = false;
    private String result;
    private boolean logout = false;
    private String name;
    private String surname;
    private String ssn;
    private String email;
   // inject tillåter beanen att hanter/kolla ifall nuvarande bean är tranist eller långvara
    @Inject 
    private Conversation conversation;
    
/**
 * Denna metod kollar ifall konversationen var transist om den är så sätt den till lånvarig
 * conversations är unique för varje browser tab och stängs när http seasion slutar därför threed safe
 * Om conversation är långvarig state i slutet av en jsf request så blit context inte förstört 
 */
    
    private void startConversation() {
        if (conversation.isTransient()) { 
            //gör den nyvarnde conversationen långvarig
            conversation.begin();
        }
    }
 /**
 *  Denna metod kolla ifall conversation inte är transist(kortvarig) end() gör så att den blir kortvarig  
 *  Om conversation är tranist i slutet av en jsf request så förstörs den 
 */
    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
/**
 * Denna metod  skriver ut felmedelanded och gör conversation till transist/kortvarigt 
 * @param e 
 */
    
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }
    /**
     * metoden init anropar method som finns init som finns i controller vilket lägger in admin först i databasen vid start av programmet 
     */
    public void init(){
        controller.init();
    }
   /**
    * Detta är inloggnings funktionen 
    * Skickar in usernamn och password till controllern som returnerar en string 
    * jämför sedan denna string med olika förhållande 
    * @return tom string
    */

    public String login() {
        try {
            startConversation(); // kollar om conversation är transist ifall så gör den till långvarig
            transactionFailure = null;
            admin = false;
            succes = false;
            logout = false;
            account = null;
            result = controller.loginC(username, password); // anropar metoden login i controllern
            if (result.equals("Done")) {
                account = controller.getAccount(username);
            }
            if (account != null && account.getUsername().equals("admin")) {
                admin = true;
            } else if (account != null) { // så länge inte account är null 
                succes = true;
            }

        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }
   /**
    * Denna metod skickar in sex parametrar till controllerna 
    * Returnerar  en string från controllern  
    * @return  en tom string 
    * 
    */
    public String newAccount() {
        try {
            startConversation(); // kollar om conversation är transist ifall så gör den till långvarig
            transactionFailure = null;
            succes = false;
            admin = false;
            logout = false;
            account = null; 
            result = controller.newAccount(username, password,name, surname, ssn,email); //skickar in till controllern
            
            if (result.equals("Done")) {
                account = controller.getAccount(username);
            }
            if (account != null && account.getUsername().equals("admin")) {//man kollar ifall man är admin här 
                admin = true;
            } else if (account != null) {
                succes = true;  // används sedan för att gå till clicent
            }

        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }
 /**
  * Denna metod är logout funktionen som först anropar startConversation metoden för att kolla ifall conversation är transist 
  * sett den till långvarit och sedan logout som true vilket leder till att man kommer till logreg xhtml
  * @return tom string 
  */
    public String logout() {
        try {
            startConversation();
            transactionFailure = null;

            logout = true;

        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }
    /**
     * Denna metod hämtar från controllern(i databasen) en lista och sedan printar ut den enligt namn och email
     * @return en list 
     */
    public String getList() {
        succes = false;
        List<Account> accounts = controller.getAccounts();
        String list = "";
        for (Account acc : accounts) {
            list = list +"Name: "+ acc.getName() + "\n \tEmail: " + acc.getEmail() + "\n\t  ";
        }
        return list;
    }
    
    // setter and getters metoder 
   
    public boolean getClintsuccess() { // till client 
        return succes;
    }
    
    public boolean getAdmin() {
        return admin;
    }
      public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Returns the latest thrown exception.
     */
    public Exception getException() {
        return transactionFailure;
    }
    /**
     * This return value is needed because of a JSF 2.2 bug. Note 3 on page 7-10
     * of the JSF 2.2 specification states that action handling methods may be
     * void. In JSF 2.2, however, a void action handling method plus an
     * if-element that evaluates to true in the faces-config navigation case
     * causes an exception.
     * @return an empty string.
     */
    private String jsf22Bugfix() {
        return "";
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return null;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return null;
    }
    public accountInterface getAccount() {
        return account;
    }
    public boolean getLogout() {
        return logout;
    }
    public String getResult(){
        return result;
    }
}
