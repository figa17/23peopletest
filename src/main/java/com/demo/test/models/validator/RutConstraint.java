package com.demo.test.models.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Felipe González Alfaro on 22-03-20.
 */
@Documented
@Constraint(validatedBy = RutValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RutConstraint {
    String message() default "Invalid Rut";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
