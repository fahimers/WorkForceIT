package controller;

import model.Account;
import model.accountInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;
/**
 * Den här classen är controllern och här så skriver vi till databasen och får
 * information från datbasen. Den kommuniserar med viewn
 * @author Elias
 */

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {

    @Size() 
    @PersistenceContext(unitName = "dataOne")
    private EntityManager em; 
    // Definar en bycript workload 
    private static int workload = 12;
    private String salt;
    private boolean done = true;
    private static String adminpass = "admin";
    private String adminPassHashed;
    
    /** 
     *  Denna metod lägger in admin först i databasen och blir anropad i viewn
     *  Använder säg även av Bycript och en salt
     * 
     */
    public void init() {
        salt=BCrypt.gensalt(workload);
        adminPassHashed= BCrypt.hashpw(adminpass, salt);
        if (done) {
            long a =1;// skickar en 1 för att admin ska vara först in i databasen.
            em.persist(new Account("admin", adminPassHashed,a,"nadmin","adminson","9205143","admin@gmail.com"));
            done = false;
        }
    }
    /**
     * Denna funktion använder sig två parametrar och blir anropad i viewn 
     * Funktionen använder sig också utav Bcrypt för att jämföra 
     * @param username usernamn är primerykey den använder för att hitta i datbasen 
     * @param password man jämför det inskrivna passworden med det som finns lagrad i databasen
     * @return en string 
     */
    
    
    public String Login(String username, String password) { // tar in två strings
        boolean password_verified = false;
        accountInterface account = em.find(Account.class, username);
        if(account == null) {
           
            return "Wrong username or password";
        }
        if(null == account.getPassword() || !account.getPassword().startsWith("$2a$"))
        {
             return "Wrong username or password";
        }
        password_verified = BCrypt.checkpw(password, account.getPassword());
        if(password_verified== true){
            return "Done";
        }
         if(password_verified== false){
            return "Wrong username or password";
        }
        return null;       
    }
    /**
     * Detta är en funktion som lägger in en ny användare till databasen och gör om plain text passworden till hash med salt 
     * @param username primery keys man använder den sedan för att hita i data basen 
     * @param password passworden til en hash med bcrypt och salt 
     * @param name
     * @param surname
     * @param ssn
     * @param email email är satt med anotation unique
     * @return  en string 
     */
    public String newAccount(String username, String password,String name,String surname, String ssn,String email)  {
        
        accountInterface Account1 = em.find(Account.class, username);// letar efter  primary key i data bassen med entety manger

        if (Account1 != null) { // om accountet med det usernamn finns 
       
            return "Account already exist"; // returnera detta
        }
        salt=BCrypt.gensalt(workload); //skapar salt med workloead 12
        String hased_password = BCrypt.hashpw(password, salt);// hashed bcrypt.
        
        long b =2;
        
        Account1 = new Account(username,hased_password,b, name, surname,ssn,email);
        em.persist(Account1); // persist till databasen 
        return "Done";
    }
    /**
     * @return returnerar en list från datbasen som finns i databasen 
     */
    public List<Account> getAccounts(){
        List<Account> account = em.createQuery("from Account e", Account.class).getResultList();
        return account;
    }
    /**
     * Denna funktion letar efter name i databasen och returnerar det som den hitar
     * @param name 
     * @return 
     */
    public accountInterface getAccount(String name) {
        return em.find(Account.class, name);
    }  
}
