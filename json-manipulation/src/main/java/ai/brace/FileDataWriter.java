package ai.brace;

import ai.brace.data.FileData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDataWriter {
    private FileData toWrite;
    private String fileName;
    private Path writePath;
    private Gson gson;

    public FileDataWriter(FileData toWrite, String fileName) throws URISyntaxException, IOException {
        this.toWrite = toWrite;
        this.fileName = fileName;
        this.writePath = Paths.get(ClassLoader.getSystemClassLoader().getResource(this.fileName).toURI());
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Writes the serialized representation of `toWrite` to disk.
     * File will be located at build/resources/main/output.json.
     *
     * @throws IOException
     */
    public void write() throws IOException {
        String jsonString = gson.toJson(toWrite);
        Files.writeString(writePath, jsonString);
    }
}
