package br.com.smartcoders.fixturegen;

import static br.com.smartcoders.fixturegen.FixtureGen.file;
import static br.com.smartcoders.fixturegen.FixtureGen.from;
import static br.com.smartcoders.fixturegen.FixtureGen.yaml;
import junit.framework.Assert;

import org.junit.Test;

import br.com.smartcoders.fixturegen.tests.animals.Cat;

public class FixtureGenTest {
  
  @Test
  public void shouldLoadFile() {
    String expectedContent = "cat:\n  name: Kit II the mission\n  birth: 2009-05-22\n  mother: Kit I\n\noldCat:\n  name: Kit I\n  birth: 1982-06-24\n  mother: Flufy\n\ndog:\n  name: Spike\n  birth: 2000-07-26\n  toy: plastic bone\n  age: 10";
    Assert.assertEquals(expectedContent, file("animals.fixture.yaml"));
  }
  
  @Test
  public void shouldBuildCatFromYamlFixture() {
    FixtureLocator fixtureLocator = from(yaml(file("animals.fixture.yaml")));
    Fixture catFixture = fixtureLocator.withId("cat");
    Cat cat = catFixture.into(Cat.class);
    Assert.assertEquals("Kit II the mission", cat.getName());
  }
  
}
