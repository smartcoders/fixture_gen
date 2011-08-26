package br.com.smartcoders.fixturegen;

import java.util.Map;

import br.com.smartcoders.fixturegen.fixturelocator.impl.MapFixtureLocator;

public class FixtureGen {
  
  public static FixtureLocator from(Map<String, Object> source) {
    return new MapFixtureLocator(source);
  }
  
  public static FixtureLocator from(FixtureSource source){
//    return new MapFixtureLocator(source);
    return null;
  }
  
  public static FixtureSource yaml(String yaml){
    return null;
  }
  
  public static String file(String string) {
    return null;
  }

}
