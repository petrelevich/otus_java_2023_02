package ru.otus.mainpackage.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;


@ConfigurationProperties(prefix = "application")
public class AppConfigForConfigProps {

    private final String paramName;
    private final String defaultValue;
    @ConstructorBinding
    public AppConfigForConfigProps(String paramName, @DefaultValue("someDefVal") String defaultValue) {
        this.paramName = paramName;
        this.defaultValue = defaultValue;
    }

    public String getParamName() {
        return paramName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
