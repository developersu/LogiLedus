package logiled.Config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import logiled.Controllers.Model.LoRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoRuleDeSerializer extends StdDeserializer<LoRule> {

    public LoRuleDeSerializer(){
        this(null);
    }

    public LoRuleDeSerializer(Class<LoRule> aRule){
        super(aRule);
    }

    @Override
    public LoRule deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        List<String> values = new ArrayList<>();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        final byte red = (byte) Integer.parseInt(node.get("RED").asText(), 16);
        final byte green = (byte) Integer.parseInt(node.get("GREEN").asText(), 16);
        final byte blue = (byte) Integer.parseInt(node.get("BLUE").asText(), 16);

        Iterator<JsonNode> iterator = node.get("Codes").elements();
        while (iterator.hasNext())
            values.add(iterator.next().asText());

        return new LoRule(red, green, blue, values.toArray(new String[0]));
    }

    /*
    @Override
    public void serialize(LoRule loRule, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("RED", String.format("%02x", loRule.getRed()));
        jsonGenerator.writeStringField("GREEN", String.format("%02x", loRule.getGreen()));
        jsonGenerator.writeStringField("BLUE", String.format("%02x", loRule.getBlue()));
        jsonGenerator.writeArrayFieldStart("Codes");
        for (String s : loRule.getKeyLedCode())
            jsonGenerator.writeString(s);
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
     */
}
