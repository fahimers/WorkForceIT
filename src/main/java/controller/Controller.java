package controller;

import model.Account;
import model.accountInterface;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.mindrot.jbcrypt.BCrypt;

/**
 * A controller. All calls to the model that are executed because of an action
 * 
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {

    @PersistenceContext(unitName = "dataOne")
    private EntityManager em; 
    // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
    private static int workload = 12;
    private String salt;
    private boolean done = true;
    private static String adminpass = "admin";
    private String adminPassHashed;

    public String Login(String username, String password) { // tar in två strings
        boolean password_verified = false;
        accountInterface account = em.find(Account.class, username);
        if(account == null) {
            //throw new EntityNotFoundException("No such account");
            return "Wrong username or password";
        }
        if(null == account.getPassword() || !account.getPassword().startsWith("$2a$"))
        {
             return "Wrong username or password";
        }
        /*    
        if(!account.getPassword().equals(password)) {
            //throw new EntityNotFoundException("Wrong username or password");
            return "Wrong username or password";
        }*/
        password_verified = BCrypt.checkpw(password, account.getPassword());
        if(password_verified== true){
            return "Done";
        }
         if(password_verified== false){
            return "Wrong username or password";
        }
        return null;       
    }
    public String newAccount(String username, String password,String name,String surname, String ssn,String email) {
        accountInterface Account1 = em.find(Account.class, username);// letar efter  primary key i data bassen med entety manger
       /* accountInterface emailtest = em.getReference(Account.class, username);
        accountInterface account2 = em.find(Account.class, username);
        String test = emailtest.getEmail();
        */
         List<Account> accounts = em.createQuery("from Account m", Account.class).getResultList();
        if (Account1 != null) {
            //throw new EntityNotFoundException("Account already exist");
            return "Account already exist";
        }
        salt=BCrypt.gensalt(workload);
        String hased_password = BCrypt.hashpw(password, salt);
        
        for(Account acc: accounts){
            if(accounts.contains(email) )
            {
                return "Email already exist";
            }
        }           
        long b =2;
        Account1 = new Account(username,hased_password,b, name, surname,ssn,email);
        em.persist(Account1); // persist till databasen 
        return "Done";
    }
    public List<Account> getAccounts(){
        List<Account> account = em.createQuery("from Account m", Account.class).getResultList();
        return account;
    }
    public accountInterface getAccount(String name) {
        return em.find(Account.class, name);
    }
    // Denna metod lägger in admin först i databasen och blir anropad i viewn
    public void init() {
        salt=BCrypt.gensalt(workload);
        adminPassHashed= BCrypt.hashpw(adminpass, salt);
        if (done) {
            long a =1;// skickar en 1 för att admin ska vara först in i databasen.
            
            em.persist(new Account("admin", adminPassHashed,a,"admin","adminson","9205143218","admin@gmail.com"));
            done = false;
        }
    }
}
