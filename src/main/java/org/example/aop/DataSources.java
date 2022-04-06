package org.example.aop;

public enum DataSources {
    DATA_SOURCE_1,
    DATA_SOURCE_2;

    public static DataSources defaultDataSource() {
        return DATA_SOURCE_1;
    }
}
