package logiledus.Config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import logiledus.Controllers.Model.LoRule;

import java.io.IOException;

public class LoRuleSerializer extends StdSerializer<LoRule> {

    public LoRuleSerializer(){
        this(null);
    }

    public LoRuleSerializer(Class<LoRule> aRule){
        super(aRule);
    }

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
}
