package br.com.smartcoders.fixturegen.fixturelocator.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;

import br.com.smartcoders.fixturegen.Fixture;
import br.com.smartcoders.fixturegen.FixtureLocator;
import br.com.smartcoders.fixturegen.fixture.impl.DefaultFixtureImpl;

public class YamlFixtureLocator implements FixtureLocator {
  
  private final Map<String, Fixture> fixtureMap;
  
  public YamlFixtureLocator() {
    fixtureMap = new HashMap<String, Fixture>();
  }
  
  public YamlFixtureLocator(String source) {
    this();
    buildFixtureMap(source);
  }
  
  @SuppressWarnings("unchecked")
  private void buildFixtureMap(String source) {
    Yaml yaml = new Yaml();
    Map<String, Object> mapSource = (Map<String, Object>) yaml.load(source);
    Fixture fixture;
    for (Entry<String, Object> entry : mapSource.entrySet()) {
      fixture = new DefaultFixtureImpl((Map<String, Object>) entry.getValue());
      fixtureMap.put(entry.getKey(), fixture);
    }
  }
  
  @Override
  public Fixture withId(String fixtureId) {
    return fixtureMap.get(fixtureId);
  }
  
}
