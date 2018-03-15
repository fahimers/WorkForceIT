

package model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueEmailValidation implements ConstraintValidator<UniqueEmail, String>{

    @Autowired
    private AccountRepository accountR;
    
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
      
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if(accountR ==null){
          return true;
      }  
      return accountR.findByName(value)==null;
    }
}
