package logiled.Config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.awt.*;
import java.io.IOException;

public class HexDeserializer extends StdDeserializer<Byte> {

    public HexDeserializer(){
        this(null);
    }

    public HexDeserializer(Class<?> aByte){
        super(aByte);
    }

    @Override
    public Byte deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return (byte) Integer.parseInt(jsonParser.getValueAsString(), 16);
    }
}
