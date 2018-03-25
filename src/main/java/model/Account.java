package model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
//import model.UniqueEmail;

/**
 * Entity class representerar tabler i databasen
 * persistent instance varibale måst deklarars som privat och kan bara nåns direkt av methoderna i enitity classen
 * 
 * @author Elias
 */
@Entity
public class Account implements Serializable, accountInterface {    //serliazble eftersom det tillåter application att säkert spara store och restore the state oberonde av vm eller andra platformar.
    private static final long serialVersionUID = 1L;
    //primery key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // automtiskt allicated av objectDB
    private String username; 
   
    @Column(unique=true)     
    //@UniqueEmail( message= "This email already exist")       
     String email;
    
    @OneToOne()
    @JoinColumn(name = "ROLE_ID")
    private Role role_id;

    public Role getRole_id() {
        return role_id;
    }

    public void setRole_id(Role role_id) {
        this.role_id = role_id;
    }
    // man kan även med beans göra validation some tex @Size(min=2, max=10)
    private String password; 
    private Long id;
    private String name;
    private String surname;
    private String ssn;
       
    //public default kontruktor utan argument för att vara entity tillåter lätt intiering och editing av framework.
    public Account(){     
        
    }  
    /**
     * Konstruktor som tar emot dessa parametrar.
     * @param username
     * @param password
     * @param id
     * @param name
     * @param surname
     * @param ssn
     * @param email 
     */
    public Account(String username, String password, Long id, String name, String surname, String ssn, String email){
        this.username = username;
        this.password = password;
        
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        
        this.email = email;
    }
    // get och set methoder som följer standard nameing convention tillåter så att man kan få en updatering om beans states i frameworken
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }
      public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    @Override
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }    
}
