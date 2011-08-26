package br.com.smartcoders.fixturegen.fixturelocator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.smartcoders.fixturegen.Fixture;
import br.com.smartcoders.fixturegen.FixtureLocator;
import br.com.smartcoders.fixturegen.fixture.impl.DefaultFixtureImpl;

public class MapFixtureLocator implements FixtureLocator {
  
  private Map<String, Fixture> fixtureMap;
  
  public MapFixtureLocator() {
    fixtureMap = new HashMap<String, Fixture>();
  }
  
  public MapFixtureLocator(Map<String, Object> source) {
    this();
    buildFixtureMap(source);
  }
  
  private void buildFixtureMap(Map<String, Object> source) {
    for (Entry<String, Object> entry : source.entrySet()) {
      Fixture fixture = new DefaultFixtureImpl(extractPropertyMap(entry.getValue()));
      fixtureMap.put(entry.getKey(), fixture);
    }
  }
  
  private Map<String, Object> extractPropertyMap(Object sourceObject) {
    HashMap<String, Object> propertyMap = new HashMap<String, Object>();
    Class<? extends Object> sourceClass = sourceObject.getClass();
    
    for (Field field : sourceClass.getDeclaredFields()) {
      field.setAccessible(true);
      
      String fieldName = field.getName();
      Object value = extractValue(sourceObject, fieldName);
      
      propertyMap.put(fieldName, value);
    }
    return propertyMap;
  }
  
  private Object extractValue(Object sourceObject, String fieldName) {
    try {
      Method propertyAccessor = sourceObject.getClass().getMethod(buildPropertyName(fieldName));
      return extractPropertyValue(sourceObject, propertyAccessor);
    }
    catch (NoSuchMethodException e) {
      return extractFieldValue(sourceObject, fieldName);
    }
  }
  
  private String buildPropertyName(String fieldName) {
    return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }
  
  private Object extractPropertyValue(Object source, Method propertyAccessor) {
    try {
      return propertyAccessor.invoke(source);
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
    return null;
  }
  
  private Object extractFieldValue(Object source, String fieldName) {
    try {
      Field field = source.getClass().getField(fieldName);
      field.setAccessible(true);
      return field.get(source);
    }
    catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public Fixture withId(String fixtureId) {
    return fixtureMap.get(fixtureId);
  }
  
}
