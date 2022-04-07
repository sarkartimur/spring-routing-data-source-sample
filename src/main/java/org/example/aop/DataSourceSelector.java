package org.example.aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
public @interface DataSourceSelector {
    DataSources value() default DataSources.DEFAULT;
}
