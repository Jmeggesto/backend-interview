package ai.brace.converters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Instant;


public class EpochMillisToISOConverter extends TypeAdapter<Integer> {
    @Override
    public Integer read(JsonReader in) throws IOException {
        return in.nextInt();
    }

    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        String val = Instant.ofEpochSecond(value).toString();
        out.value(val);
    }
}
