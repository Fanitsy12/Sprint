package com.myframework.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE)            
public @interface Info {
    String auteur();
    double version() default 1.0;
    String projet() default "projet-personnel";
    String uid() default "uid-0000";
    String date() default "";      // format ISO: YYYY-MM-DD (facultatif)
    String contact() default ""; 
}
