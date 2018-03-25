
package model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account, Integer> {
    
    Account findByName(String email);
    
}
