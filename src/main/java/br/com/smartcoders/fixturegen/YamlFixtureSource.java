package br.com.smartcoders.fixturegen;

import br.com.smartcoders.fixturegen.fixturelocator.impl.YamlFixtureLocator;

public class YamlFixtureSource implements FixtureSource {
  
  private final String yaml;
  
  public YamlFixtureSource(String yaml) {
    this.yaml = yaml;
  }
  
  @Override
  public FixtureLocator getLocator() {
    return new YamlFixtureLocator(yaml);
  }
  
}
