

package model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Elias
 */
public class UniqueEmailValidation implements ConstraintValidator<UniqueEmail, String>{

    @Autowired
    private AccountRepository accountR;
    
    /**
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
      
    }

    /**
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if(accountR ==null){
          return true;
      }  
      return accountR.findByName(value)==null;
    }
}
