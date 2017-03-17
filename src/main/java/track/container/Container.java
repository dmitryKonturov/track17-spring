package track.container;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {
    private Map<String, Object> objByName = new HashMap<>();
    private Map<String, Object> objByClass = new HashMap<>();

    private Map<String, Bean> notConstructedYet;
    private Set<String> underConstruction;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) throws InvalidConfigurationException {
        notConstructedYet = new HashMap<>();
        underConstruction = new HashSet<>();
        for (Bean bean : beans) {
            notConstructedYet.put(bean.getId(), bean);
        }
        for (Bean bean : beans) {
            instantiateBean(bean.getId());
        }
    }

    private String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    private Object getOrInstantianteByName(String name) throws InvalidConfigurationException {
        Object toReturn = objByName.get(name);
        if (toReturn != null) {
            return toReturn;
        }
        instantiateBean(name);
        return objByName.get(name);
    }

    private void instantiateBeanObject(Bean bean) throws InvalidConfigurationException {
        String className = bean.getClassName();
        Class clazz;
        Object object;

        try {
            clazz = Class.forName(className);
            object = clazz.newInstance();
        } catch (Exception e) {
            throw new InvalidConfigurationException(e);
        }

        try {
            Property property;
            for (Map.Entry<String, Property> entry : bean.getProperties().entrySet()) {
                property = entry.getValue();
                String name = entry.getKey();

                String setterName = "set" + capitalizeFirstLetter(name);
                Method setter = null;
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.getName().equals(setterName)) {
                        setter = method;
                        break;
                    }
                }

                if (setter == null) {
                    throw new InvalidConfigurationException("No such method");
                }

                if (property.getType() == ValueType.REF) {
                    Object toSet = getOrInstantianteByName(property.getValue());
                    setter.invoke(object, toSet);
                } else {
                    Field field = clazz.getDeclaredField(name);
                    String value = property.getValue();
                    switch (field.getType().getName()) {
                        case "Boolean":
                        case "boolean":
                            setter.invoke(object, Boolean.parseBoolean(value));
                            break;
                        case "byte":
                        case "Byte":
                            setter.invoke(object, Byte.parseByte(value));
                            break;
                        case "int":
                        case "Integer":
                            setter.invoke(object, Integer.parseInt(value));
                            break;
                        case "short":
                        case "Short":
                            setter.invoke(object, Short.parseShort(value));
                            break;
                        case "long":
                        case "Long":
                            setter.invoke(object, Long.parseLong(value));
                            break;
                        case "float":
                        case "Float":
                            setter.invoke(object, Float.parseFloat(value));
                            break;
                        case "double":
                        case "Double":
                            setter.invoke(object, Double.parseDouble(value));
                            break;
                        case "java.lang.String":
                            setter.invoke(object, value);
                            break;
                        default:
                            throw new Exception("cannot set the field " + field.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new InvalidConfigurationException(e);
        }

        objByName.put(bean.getId(), object);
        objByClass.put(bean.getClassName(), object);
    }

    private void instantiateBean(String beanName) throws InvalidConfigurationException {

        Object beanObject = objByName.get(beanName);
        if (beanObject != null) {
            return;
        }
        if (underConstruction.contains(beanName)) {
            throw new InvalidConfigurationException("Cyclic reference");
        }

        Bean bean = notConstructedYet.get(beanName);
        if (bean == null) {
            throw new InvalidConfigurationException("Configuration contains reference to non-existing bean");
        }

        underConstruction.add(beanName);

        instantiateBeanObject(bean);

        notConstructedYet.remove(beanName);
    }

    /**
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) {
        return objByName.get(id);
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        return objByClass.get(className);
    }
}
