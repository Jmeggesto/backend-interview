package ai.brace.data;

import ai.brace.converters.EpochMillisToISOConverter;
import ai.brace.converters.UUIDConverter;
import com.google.gson.annotations.JsonAdapter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A wrapper class to store text file data.
 */
public class FileData {

    // AUTHOR'S NOTE:
    // I chose to use @JsonAdapter on `uuid` and `lastModified` so that any execution of a
    // JSON serialization action guarantees that the output has the desired data,
    // rather than requiring the developer to make sure they've modified the FileData properly.

    private String version;
    @JsonAdapter(UUIDConverter.class)
    private String uuid;
    @JsonAdapter(EpochMillisToISOConverter.class)
    private Integer lastModified;
    private String title;
    private String author;
    private String translator;
    private String releaseDate;
    private String language;
    private List<TextItem> textArray;

    public FileData(String version,
                    String uuid,
                    Integer lastModified,
                    String title,
                    String author,
                    String translator,
                    String releaseDate,
                    String language,
                    List<TextItem> textArray) {
        this.version = version;
        this.uuid = uuid;
        this.lastModified = lastModified;
        this.title = title;
        this.author = author;
        this.translator = translator;
        this.releaseDate = releaseDate;
        this.language = language;
        this.textArray = textArray;
    }

    public static FileData merge(FileData left, FileData right) {
        List<TextItem> mergedTextArray = Stream.concat(left.textArray.stream(), right.textArray.stream())
                .sorted(Comparator.comparingInt(TextItem::getId))
                .collect(Collectors.toList());

        FileData older, newer;
        if (left.lastModified < right.lastModified) {
            older = left;
            newer = right;
        } else {
            older = right;
            newer = left;
        }

        return new FileData(
                newer.version != null ? newer.version : older.version,
                newer.uuid != null ? newer.uuid : older.uuid,
                newer.lastModified != null ? newer.lastModified : older.lastModified,
                newer.title != null ? newer.title : older.title,
                newer.author != null ? newer.author : older.author,
                newer.translator != null ? newer.translator : older.releaseDate,
                newer.releaseDate != null ? newer.releaseDate : older.releaseDate,
                newer.language != null ? newer.language : older.language,
                mergedTextArray
        );
    }

    public static FileData getOlder(FileData left, FileData right) {
        return left.lastModified < right.lastModified ? left : right;
    }

    public static FileData getNewer(FileData left, FileData right) {
        return left.lastModified > right.lastModified ? left : right;
    }

    public String getVersion() {
        return version;
    }

    public String getUuid() {
        return uuid;
    }

    public Integer getLastModified() {
        return lastModified;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTranslator() {
        return translator;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public List<TextItem> getTextArray() {
        return textArray;
    }
}

