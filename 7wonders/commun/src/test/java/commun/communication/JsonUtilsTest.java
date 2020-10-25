package commun.communication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilsTest
{
    private JsonUtils jsonUtils;
    private ArrayList<Integer> arrayList;

    @BeforeEach
    void init ()
    {
        this.jsonUtils = new JsonUtils();
        this.arrayList = new ArrayList<>();
        this.arrayList.add(0);
        this.arrayList.add(1);
        this.arrayList.add(2);
        this.arrayList.add(3);
    }

    @Test
    void testSerialize ()
            throws IOException
    {
        String expected = "{\"object\":[0,1,2,3],\"type\":\"java.util.ArrayList\"}";
        assertEquals(expected, this.jsonUtils.serialize(this.arrayList));
    }

    @Test
    void testDeserialize ()
            throws IOException, ClassNotFoundException
    {
        assertEquals(this.arrayList.toString(), this.jsonUtils.deserialize(this.jsonUtils.serialize(this.arrayList), ArrayList.class).toString());
    }
}
