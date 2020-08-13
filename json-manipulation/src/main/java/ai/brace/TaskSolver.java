package ai.brace;

import ai.brace.data.FileData;
import ai.brace.data.TextItem;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;

public class TaskSolver {

    private static final String PUNCTUATION_REGEX = "[\\.\\,\"]";

    private FileData a1, a2;

    // AUTHOR'S NOTE:
    // I used Java streams for the solutions here, for elegance
    // of implementation and heightened readability.
    // If the performance of this section of code was critically important,
    // there might be a case for avoiding streams altogether.
    // However, such premature optimization seems unnecessary in this case.

    // Additionally, another perfectly valid solution would be to have a class
    // that emits the correct values and another that prints them to an output.
    // That would make it easier to test that the task solver methods
    // are producing the correct output. However,

    public TaskSolver(FileData a1, FileData a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    /**
     * Sorts and prints each textData of a1's textArray by ID, in ascending order.
     */
    public void solveTaskOne() {
        System.out.println("---------- TASK ONE ----------");
        a1.getTextArray()
                .stream()
                .sorted(Comparator.comparingInt(TextItem::getId))
                .forEach(textItem -> System.out.println(textItem.getTextdata()));
    }

    /**
     * Merges, sorts, and prints the textdata in a1 and a2's textArrays by ID, in ascending order.
     */
    public void solveTaskTwo() {
        System.out.println("---------- TASK TWO ----------");
        Stream<TextItem> stream1 = a1.getTextArray().stream();
        Stream<TextItem> stream2 = a2.getTextArray().stream();
        Stream.concat(stream1, stream2)
                .sorted(Comparator.comparingInt(TextItem::getId))
                .forEach(textItem -> System.out.println(textItem.getTextdata()));
    }

    public void solveTaskThree() {
        System.out.println("---------- TASK THREE ----------");
        Stream<TextItem> stream1 = a1.getTextArray().stream();
        Stream<TextItem> stream2 = a2.getTextArray().stream();
        Map<String, Long> map = Stream.concat(stream1, stream2)
                .flatMap(textItem -> {
                    return Stream.of(textItem
                            .getTextdata()
                            .toLowerCase()
                            .replaceAll(PUNCTUATION_REGEX, "")
                            .split(" "));

                })
                .collect(Collectors.groupingBy(Function.identity(), counting()));
        map.forEach((key, value) -> {
            System.out.printf("(%s) : %d\n", key, value);
        });
    }

    public void solveTaskFour(String outputFileName) throws Exception {
        System.out.println("---------- TASK FOUR ----------");

        FileData merged = FileData.merge(a1, a2);
        new FileDataWriter(merged, outputFileName).write();
    }
}
