package commun.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * SocketUtils est une classe qui sert a deserialiser
 * et a serialiser les paquets recus par le serveur
 */
public class JsonUtils
{
    private ObjectMapper mapper;

    /** Constructeur */
    public JsonUtils ()
    { this.mapper = new ObjectMapper(); }

    /**
     * Permet de tranformer une String sous format JSON en objet
     * instancie de classe tClass
     * @param json String JSON a deserialiser
     * @param tClass Class qui correspond a la String JSON
     */
    public <T> T deserialize (String json, Class<T> tClass)
            throws IOException, ClassNotFoundException
    {
        JsonNode rootNode = mapper.readTree(json);
        return tClass.cast(mapper.treeToValue(rootNode.get("object"),Class.forName(rootNode.get("type").asText())));
    }

    /**
     * Permet de tranformer un objet en String sous format JSON
     * @param object Objet instancie a transformer en String JSON
     */
    public String serialize (Object object)
    { //manualy add type
        try {

            ObjectNode node = mapper.createObjectNode();
            node.set("object",mapper.valueToTree(object));
            node.put("type",object.getClass().getCanonicalName());

            return mapper.writeValueAsString(node);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;//NOT REACHABLE CASE (excpt LOOP)
    }
}