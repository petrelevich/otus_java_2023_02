package ru.otus.appcontainer;

import org.javatuples.Pair;
import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


public class AppComponentsContainerImpl implements AppComponentsContainer {

    //private final List<Object> appComponents = new ArrayList<>(); поиск класса переделал на canonical name. теперь это не надо
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<String, Object> appComponentsByCanonicalName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public AppComponentsContainerImpl(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);
        var cl = classes.stream()
                .map(m -> Pair.with(m, m.getAnnotation(AppComponentsContainerConfig.class)))
                .sorted(Comparator.comparingInt(p -> p.getValue1().order()))
                .map(p -> p.getValue0())
                .collect(Collectors.toList());
        for (var confClass : cl) {
            try {
                processConfig(confClass);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private Object getConfigObj(Class<?> initialConfigClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        var ctor = initialConfigClass.getDeclaredConstructors()[0];
        if (ctor.getParameterTypes().length > 0) {
            throw new IllegalArgumentException(String.format("%s have haven't constructors without params!", initialConfigClass.getCanonicalName()));
        }
        return ctor.newInstance(new Object[0]);
    }

    @SuppressWarnings("unchecked")
    private void processImplementations(Class<?> cls, Object obj) {
        Reflections reflections = new Reflections(cls.getPackageName());
        Set<Class<?>> classes = reflections.getSubTypesOf((Class<Object>) cls);
        for (var impl : classes) {
            if (!appComponentsByCanonicalName.containsKey(impl.getCanonicalName()))
                appComponentsByCanonicalName.put(impl.getCanonicalName(), obj);
        }
    }

    private void processMethod(Class<?> configClass, Pair<Method, AppComponent> p) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        var method = p.getValue0();
        if (appComponentsByName.containsKey(p.getValue1().name())) {
            throw new IllegalArgumentException(String.format("name %s is found in container!", p.getValue1().name()));
        }
        if (appComponentsByCanonicalName.containsKey(method.getReturnType().getCanonicalName())) {
            appComponentsByCanonicalName.put(method.getReturnType().getCanonicalName(), null); //дублирующийся компонент, двусмысленность
            return;
        }

        Object[] args = new Object[method.getParameterTypes().length];
        var index = 0;
        for (var parType : method.getParameterTypes()) {
            var objType = parType.getCanonicalName();
            if (appComponentsByCanonicalName.containsKey(objType)) {
                args[index] = appComponentsByCanonicalName.get(objType);
                index++;
            } else {
                throw new IllegalArgumentException(String.format("Canonical name %s not found in container!", objType));
            }
        }
        Object obj = method.invoke(getConfigObj(configClass), args);

        appComponentsByName.put(p.getValue1().name(), obj);
        appComponentsByCanonicalName.put(method.getReturnType().getCanonicalName(), obj);

        processImplementations(method.getReturnType(), obj);
    }

    private void processConfig(Class<?> configClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        checkConfigClass(configClass);
        var toProcess = Arrays.stream(configClass.getDeclaredMethods())
                .map(m -> Pair.with(m, m.getAnnotation(AppComponent.class)))
                .filter(p -> p.getValue1() != null)
                .sorted(Comparator.comparingInt(p -> p.getValue1().order()))
                .collect(Collectors.toList());

        for (var item : toProcess) {
            processMethod(configClass, item);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(Class<C> processMethod) {
        if (!appComponentsByCanonicalName.containsKey(processMethod.getCanonicalName())) {
            throw new RuntimeException(String.format("Not found class %s", processMethod.getCanonicalName()));
        }
        var c = appComponentsByCanonicalName.get(processMethod.getCanonicalName());
        if (c == null) {
            throw new RuntimeException("incorrect config");
        }
        return (C) c;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(String componentName) {
        if (!appComponentsByName.containsKey(componentName)) {
            throw new RuntimeException(String.format("Not found component %s", componentName));
        }
        return (C) appComponentsByName.get(componentName);
    }
}
