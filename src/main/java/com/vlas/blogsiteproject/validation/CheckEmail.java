package com.vlas.blogsiteproject.validation;


import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckEmail {
    public String value() default "@softclub.com";
    public String message() default "корпоративная почта должна заканчиваться на '@softclub.com' ";

    public Class<?>[] groups() default  {};
    public Class<? extends Payload>[] payload() default {};
}
