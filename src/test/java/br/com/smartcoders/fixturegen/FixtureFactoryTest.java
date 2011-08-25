package br.com.smartcoders.fixturegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import br.com.smartcoders.fixturegen.fixture.impl.DefaultFixtureImpl;
import br.com.smartcoders.fixturegen.tests.animals.Cat;

public class FixtureFactoryTest {

    private static final String CAT_ID     = "cat";
    private static final String CAT_MOTHER = "aFemaleCat";
    private static final String CAT_NAME   = "catName";
    private static final Date   CAT_BIRTH  = toDate("2009-05-20");

    private FixtureMap          animalFixtures;
    private Fixture             catFixture;
    private Cat                 cat;

    private static Date toDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-mm-dd").parse(dateString);
        }
        catch (ParseException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }

    @Before
    public void setup() {
        Cat cat = new Cat();
        cat.setName(CAT_NAME);
        cat.setBirth(CAT_BIRTH);
        cat.setMother(CAT_MOTHER);

        // this is a fixture that should be within a fixture file!
        Map<String, Object> mapSource = new HashMap<String, Object>();
        mapSource.put(CAT_ID, cat);

        // this is how I would like this call to be!
        // animalFixtures = FixtureGen.from(yaml(file("animals.fixture.yaml")));

        animalFixtures = FixtureGen.from(mapSource);
        catFixture = animalFixtures.withId(CAT_ID);
    }

    @Test
    public void aCatWithNameShouldHaveAPropertyName() {
        Assert.assertEquals(CAT_NAME, catFixture.get("name"));
    }

    @Test
    public void aCatWithBirthShouldHaveAPropertyBirth() {
        Assert.assertEquals(CAT_BIRTH, catFixture.get("birth"));
    }

    @Test
    public void whenICastAFixtureIntoCatIShouldHaveAFunctioningCatObject() {
        cat = catFixture.into(Cat.class);

        Assert.assertEquals(CAT_BIRTH, cat.getBirth());
        Assert.assertEquals(CAT_NAME, cat.getName());

    }

    @Test
    public void shouldLoadCatFixtureFromFile() throws FileNotFoundException {
        InputStream input = new FileInputStream(new File("src/test/resources/animals.fixture.yaml"));
        Yaml yaml = new Yaml();

        Map<String, Object> mapSource = (Map<String, Object>) yaml.load(input);
        Map<String, Object> catProperties = (Map<String, Object>) mapSource.get(CAT_ID);

        // TODO: Review code
        // System.out.println(FixtureGen.from(mapSource));
        // System.out.println(FixtureGen.from(catProperties));

        // TODO: Review code
        DefaultFixtureImpl defaultFixtureImpl = new DefaultFixtureImpl(catProperties);
        Assert.assertEquals("Kit II the mission", defaultFixtureImpl.into(Cat.class).getName());
    }
}
