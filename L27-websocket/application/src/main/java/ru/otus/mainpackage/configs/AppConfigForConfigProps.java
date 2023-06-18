package ru.otus.mainpackage.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;


@ConfigurationProperties(prefix = "application")
public class AppConfigForConfigProps {

    private final String paramName;

    @ConstructorBinding
    public AppConfigForConfigProps(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }


}
