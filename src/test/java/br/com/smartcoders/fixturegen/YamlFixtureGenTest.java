package br.com.smartcoders.fixturegen;

import org.junit.Before;
import static br.com.smartcoders.fixturegen.FixtureGen.*;

public class YamlFixtureGenTest extends InMemoryFixtureGenTest{
  
  @Before
  public void setup() {
    animalFixtures = FixtureGen.from(yaml(file("animals.fixture.yaml")));
    catFixture = animalFixtures.withId(CAT_ID);
  }

}
