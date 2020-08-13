package ai.brace;

import ai.brace.data.FileData;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FileMergeTest {

    private Map<String, Object> merged;
    private Map<String, Object> expected;

    // AUTHOR'S NOTE:
    // I had thought to serialize `merged` and then deserialize it again,
    // to test that the UUID values for `expected` and `merged` are different
    // and that the UUIDGenerator is working correctly.
    // However, since `lastModifiedAt` will become an ISO-string on serialization,
    // we need to serialize `merged` and then deserialize it into a plain map,
    // since attempting to deserialize into a FileData object will cause an exception
    // as an ISO-formatted String is not parseable into an integer.

    @Before
    public void setUp() throws Exception {
        FileData a1 = new FileDataProcessor("a1.json").processFileData();
        FileData a2 = new FileDataProcessor("a2.json").processFileData();

        expected = new FileDataProcessor("expectedOutput.json").processJson();
        Gson gson = new Gson();

        String jsonString = gson.toJson(FileData.merge(a1, a2));
        Map<String, Object> map = gson.fromJson(jsonString, Map.class);
        merged = map;
    }

    @Test
    public void testMergedEqualsExpected() {
        assertEquals(expected.get("version"), merged.get("version"));
        assertNotEquals(expected.get("uuid"), merged.get("uuid"));
        assertNotEquals(expected.get("lastModified"), merged.get("lastModified"));
        assertEquals(expected.get("title"), merged.get("title"));
        assertEquals(expected.get("author"), merged.get("author"));
        assertEquals(expected.get("translator"), merged.get("translator"));
        assertEquals(expected.get("releaseDate"), merged.get("releaseDate"));
        assertEquals(expected.get("language"), merged.get("language"));

        List<Map<String, Object>> expectedArray = (List<Map<String, Object>>)expected.get("textArray");
        List<Map<String, Object>> mergedArray = (List<Map<String, Object>>)merged.get("textArray");

        assertTrue(expectedArray.size() > 0 && mergedArray.size() > 0);
        assertEquals(expectedArray.size(), mergedArray.size());

        for(int i = 0; i < expectedArray.size(); i++) {
            Map<String, Object> expectedItem = expectedArray.get(i);
            Map<String, Object> mergedItem = mergedArray.get(i);

            assertEquals(expectedItem.get("id"), mergedItem.get("id"));
            assertEquals(expectedItem.get("textData"), mergedItem.get("textData"));
        }
    }
}
