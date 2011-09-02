package br.com.smartcoders.fixturegen.fixture.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import br.com.smartcoders.fixturegen.Fixture;

public class DefaultFixtureImpl implements Fixture {
  
  private final Map<String, Object> fixtures;
  
  public DefaultFixtureImpl(Map<String, Object> fixtures) {
    this.fixtures = fixtures;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public <T> T get(String propertyName) throws PropertyNotFoundException {
    return (T) fixtures.get(propertyName);
  }
  
  @Override
  public <T> T into(Class<T> clazz) {
    T newBorn = newInstanceOf(clazz);
    
    for (Entry<String, Object> property : fixtures.entrySet()) {
      String propertyName = property.getKey();
      Object propertyValue = property.getValue();
      
      String setterMethodName = getSetterName(propertyName);
      
      try {
        Method setterMethod = clazz.getMethod(setterMethodName, propertyValue.getClass());
        setWithSetterMethod(newBorn, propertyValue, setterMethod);
      }
      catch (NoSuchMethodException e) {
        setDirectlyOnField(newBorn, propertyName, propertyValue);
      }
    }
    
    return newBorn;
  }
  
  private <T> void setDirectlyOnField(T newBorn, String propertyName, Object propertyValue) {
    try {
      Field field = newBorn.getClass().getField(propertyName);
      field.setAccessible(true);
      field.set(newBorn, propertyValue);
    }
    catch (NoSuchFieldException e) {
    }
    catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
  
  private <T> void setWithSetterMethod(T newBorn, Object propertyValue, Method setterMethod) {
    try {
      setterMethod.invoke(newBorn, propertyValue);
    }
    catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
  
  private String getSetterName(String propertyName) {
    return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
  }
  
  private <T> T newInstanceOf(Class<T> clazz) {
    T newBorn = null;
    try {
      newBorn = clazz.newInstance();
    }
    catch (InstantiationException e) {
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return newBorn;
  }
  
}
