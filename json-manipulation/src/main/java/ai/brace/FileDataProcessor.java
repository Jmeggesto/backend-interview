package ai.brace;

import ai.brace.data.FileData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

/**
 * A class to take in a filename and process the file into
 * a deserialized JSON object.
 * <p>
 * The file must exist in the resources/ directory of the package.
 */
public class FileDataProcessor {

    // AUTHOR'S NOTES:
    // Note 1:
    // A class like this should generally have some facility
    // for keeping track of whether the file has already been processed
    // so that errors are not encountered if subsequent calls to `processResource`
    // are made on a processor that has already processed its resource;
    // otherwise, an exception in reading from the (now closed/depleted)
    // input stream / reader could be encountered.

    // For the sake of time, I chose to encapsulate necessary functionality
    // without worrying overmuch about such error handling. However, a production
    // implementation should be more robust than this.

    // Note 2:
    // I chose to process resources in this manner (where we obtain
    // a handle to an inputStream/reader rather than reading all lines
    // of the file into memory at once) because when doing work with reading files directly,
    // I've found it's often advantageous to delay reading from the file
    // until it's absolutely necessary; for instance, the instantiation of the
    // processor class could throw an exception if the file does not exist,
    // at which point you could do more robust error handling and fail earlier.

    private Reader reader;
    private Gson gson;

    public FileDataProcessor(String filename) throws IOException {
        URL resourceURL = ClassLoader.getSystemClassLoader().getResource(filename);
        this.reader = new BufferedReader(new InputStreamReader(resourceURL.openStream()));
        this.gson = new Gson();
    }

    /**
     * Processes the input resource into an output FileData instance.
     *
     * @return an instant of the deserialized object type.
     */
    public FileData processFileData() {
        return gson.fromJson(reader, FileData.class);
    }

    /**
     * Process the input resource into a raw JSON map.
     * @return
     */
    public Map<String, Object> processJson() { return gson.fromJson(reader, Map.class); }
}
