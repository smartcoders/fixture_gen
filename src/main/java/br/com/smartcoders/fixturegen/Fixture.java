package br.com.smartcoders.fixturegen;

public interface Fixture {
  
  <T> T get(String propertyName) throws PropertyNotFoundException;
  
  <T> T into(Class<T> clazz);
  
  @SuppressWarnings("serial")
  public class PropertyNotFoundException extends RuntimeException {
    
  }
}
