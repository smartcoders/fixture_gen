package br.com.smartcoders.fixturegen;

import java.io.InputStream;
import java.util.Map;

import br.com.smartcoders.fixturegen.fixturelocator.impl.MapFixtureLocator;

public class FixtureGen {
  
  public static FixtureLocator from(Map<String, Object> source) {
    return new MapFixtureLocator(source);
  }
  
  public static FixtureLocator from(FixtureSource source) {
    return source.getLocator();
  }
  
  public static FixtureSource yaml(String yaml) {
    return new YamlFixtureSource(yaml);
  }
  
  public static String file(String fileName) {
    String fileContent = null;
    
    try {
      if (fileName != null) {
        InputStream stream = FixtureGen.class.getClassLoader().getResourceAsStream(fileName);
        int numberOfBytes = stream.available();
        if (numberOfBytes > 0) {
          byte[] utf8Bytes = new byte[numberOfBytes];
          stream.read(utf8Bytes);
          fileContent = new String(utf8Bytes, "UTF8");
        }
      }
    }
    catch (Exception ex) {
    }
    
    return fileContent;
  }
}
