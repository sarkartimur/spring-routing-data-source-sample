package org.example.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.app.RoutingDataSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class DataSourceSelectorAspect {
    private final RoutingDataSource dataSource;

    @Around("target(org.springframework.data.repository.Repository)")
    public Object setSelectDataSource(ProceedingJoinPoint jp) throws Throwable {
        // since JdkDynamicProxy does not inherit annotations, iterate over interfaces
        Optional<Class<?>> annotatedInterface = Arrays.stream(jp.getTarget().getClass().getInterfaces())
                .filter(i -> i.isAnnotationPresent(DataSourceSelector.class))
                .findFirst();
        if(annotatedInterface.isPresent()) {
            DataSourceSelector anno = annotatedInterface.get().getAnnotation(DataSourceSelector.class);
            dataSource.getContext().set(anno.value());

            try {
                return jp.proceed();
            } finally {
                dataSource.getContext().set(DataSources.DEFAULT);
            }
        } else {
            return jp.proceed();
        }
    }
}