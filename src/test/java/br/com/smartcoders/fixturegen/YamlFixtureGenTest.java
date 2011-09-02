package br.com.smartcoders.fixturegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import br.com.smartcoders.fixturegen.fixture.impl.DefaultFixtureImpl;
import br.com.smartcoders.fixturegen.tests.animals.Cat;

public class YamlFixtureGenTest {
  
  // @Override
  // @Before
  // public void setup() {
  // animalFixtures = FixtureGen.from(yaml(file("animals.fixture.yaml")));
  // catFixture = animalFixtures.withId(CAT_ID);
  // }
  
  @Test
  @SuppressWarnings("unchecked")
  public void shouldLoadCatFixtureFromFile() throws FileNotFoundException {
    InputStream input = new FileInputStream(new File("src/test/resources/animals.fixture.yaml"));
    Yaml yaml = new Yaml();
    
    Map<String, Object> mapSource = (Map<String, Object>) yaml.load(input);
    Map<String, Object> catProperties = (Map<String, Object>) mapSource.get("cat");
    
    // TODO: Review code
    // System.out.println(FixtureGen.from(mapSource));
    // System.out.println(FixtureGen.from(catProperties));
    
    // TODO: Review code
    DefaultFixtureImpl defaultFixtureImpl = new DefaultFixtureImpl(catProperties);
    Assert.assertEquals("Kit II the mission", defaultFixtureImpl.into(Cat.class).getName());
  }
  
}
