package br.com.smartcoders.fixturegen;

import java.util.Map;

import br.com.smartcoders.fixturegen.fixturemap.impl.FixtureHashMap;

public class FixtureGen {
  
  public static FixtureMap from(Map<String, Object> source){
    return new FixtureHashMap(source);
  }

}
