package logiled.Config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class HexSerializer extends StdSerializer<Byte> {

    public HexSerializer(){
        this(null);
    }

    public HexSerializer(Class<Byte> aByte){
        super(aByte);
    }

    @Override
    public void serialize(Byte aByte, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(String.format("%02x", aByte));
    }
}
