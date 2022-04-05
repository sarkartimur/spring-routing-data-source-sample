package org.example.app;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Getter
public class RoutingDataSource extends AbstractRoutingDataSource {
    private final RoutingDataSourceConfigurationProperties config;
    private final RoutingDataSourceContext context;

    public RoutingDataSource(RoutingDataSourceConfigurationProperties config) {
        this.config = config;
        this.context = new RoutingDataSourceContext();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return context.getCurrentDatasource();
    }

    @Override
    public void afterPropertiesSet() {
        Map<Object, Object> dataSources = createDataSources();
        setTargetDataSources(dataSources);
        Object ds = dataSources.values().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Datasource not defined"));
        setDefaultTargetDataSource(ds);
        super.afterPropertiesSet();
    }

    private Map<Object, Object> createDataSources() {
        return config.getParams().entrySet().stream().map(e ->
                Map.entry(e.getKey(), DataSourceBuilder.create()
                    .url(e.getValue().getUrl())
                    .username(e.getValue().getUname())
                    .password(e.getValue().getPass())
                    .build())
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public static class RoutingDataSourceContext {
        private final ThreadLocal<String> CURRENT_DATASOURCE = new ThreadLocal<>();

        public void set(String source) {
            CURRENT_DATASOURCE.set(source);
        }

        public String getCurrentDatasource() {
            return CURRENT_DATASOURCE.get();
        }
    }


    @Component
    @ConfigurationProperties(prefix = "datasource")
    @Getter
    public static class RoutingDataSourceConfigurationProperties {
        private final Map<String, DataSourceParams> params = new HashMap<>();

        @Data
        public static class DataSourceParams {
            private String url;
            private String uname;
            private String pass;
        }
    }
}