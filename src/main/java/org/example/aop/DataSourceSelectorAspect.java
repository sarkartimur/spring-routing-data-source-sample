package org.example.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.app.RoutingDataSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class DataSourceSelectorAspect {
    private final RoutingDataSource dataSource;

    @Pointcut("within(@DataSourceSelector *)")
    public void dataSourceSelectorTypePointcut() {}

    @Pointcut("@annotation(DataSourceSelector)")
    public void dataSourceSelectorMethodPointcut() {}

    @Around("dataSourceSelectorTypePointcut() || dataSourceSelectorMethodPointcut()")
    public Object setSelectDataSource(ProceedingJoinPoint jp) throws Throwable {
        DataSourceSelector anno = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(DataSourceSelector.class);
        anno = anno != null ? anno : jp.getTarget().getClass().getAnnotation(DataSourceSelector.class);
        dataSource.getContext().set(anno.value());

        try {
            return jp.proceed();
        } finally {
            dataSource.getContext().set(DataSources.defaultDataSource());
        }
    }
}