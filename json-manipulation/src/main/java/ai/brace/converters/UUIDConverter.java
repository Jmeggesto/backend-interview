package ai.brace.converters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.UUID;

public class UUIDConverter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader in) throws IOException {
        return in.nextString();
    }
    @Override
    public void write(JsonWriter writer, String value) throws IOException {
        writer.value(UUID.randomUUID().toString());
    }
}
