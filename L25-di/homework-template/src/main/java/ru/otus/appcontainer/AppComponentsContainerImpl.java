package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List <Method> methods = List.of(configClass.getDeclaredMethods());
        List <Method> methodsSorted = methods.stream().sorted(Comparator.comparingInt(x -> x.getAnnotation(AppComponent.class).order())).toList();
        for (Method method : methodsSorted){
            try {
                var params = method.getParameterTypes();
                Object [] objects = new Object[params.length];
                for (int i = 0; i<params.length; i++){
                    objects [i] = getAppComponent(params[i]);
                }
                var component = method.invoke(configClass.getConstructor().newInstance(), objects);
                appComponents.add(component);
                appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), component);

            } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object component : appComponents){
            if (componentClass.isInstance(component)){
                return (C) component;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return  (C) appComponentsByName.get(componentName);
    }
}
