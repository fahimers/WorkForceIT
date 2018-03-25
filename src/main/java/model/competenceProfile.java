package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 
 * @author Elias
 */
@Entity
public class competenceProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long competence_profile_id;
    private Account person;
    private competence competenceID;
    private int yearOfExperience;
    
    public Long getCompetence_profile_id() {
        return competence_profile_id;
    }
    public void setCompetence_profile_id(Long competence_profile_id) {
        this.competence_profile_id = competence_profile_id;
    }
    public Account getPerson() {
        return person;
    }
    public void setPerson(Account person) {
        this.person = person;
    }
    public competence getCompetenceID() {
        return competenceID;
    }
    public void setCompetenceID(competence competenceID) {
        this.competenceID = competenceID;
    }
    public int getYearOfExperience() {
        return yearOfExperience;
    }
    public void setYearOfExperience(int yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }
    public Long getId() {
        return competence_profile_id;
    }
    public void setId(Long id) {
        this.competence_profile_id = id;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (competence_profile_id != null ? competence_profile_id.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof competenceProfile)) {
            return false;
        }
        competenceProfile other = (competenceProfile) object;
        if ((this.competence_profile_id == null && other.competence_profile_id != null) || (this.competence_profile_id != null && !this.competence_profile_id.equals(other.competence_profile_id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "model.competenceProfile[ id=" + competence_profile_id + " ]";
    }   
}