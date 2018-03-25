
package model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Elias
 */
@Target(value = { ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueEmailValidation.class})

public @interface UniqueEmail {
    
    /**
     *
     * @return
     */
    public String message();

    /**
     *
     * @return
     */
    public Class<?>[] groups() default {};

    /**
     *
     * @return
     */
    public Class<? extends Payload>[] payload() default {};
   
}
