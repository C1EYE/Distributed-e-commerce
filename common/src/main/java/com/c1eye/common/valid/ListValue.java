package com.c1eye.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author c1eye
 * time 2022/3/10 20:34
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER,ElementType.TYPE_USE})
@Documented
@Constraint(validatedBy = {ListValueConstraintValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {
    String message() default "{javax.validation.constraints.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] vals() default {};
}
