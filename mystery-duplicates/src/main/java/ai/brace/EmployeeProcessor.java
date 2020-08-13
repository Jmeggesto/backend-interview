package ai.brace;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class EmployeeProcessor {
    public static Path getPathForResource(final String path) {
        try {
            return Paths.get(ClassLoader.getSystemResource(path).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        final Map<Employee, Integer> duplicateCount = new HashMap<>();

        try (Stream<String> stream = Files.lines(getPathForResource("employees.csv"))) {
            stream.forEach(line -> {
                final String[] elements = line.split(",");
                final Employee emp = new Employee(elements[0], elements[1], elements[2], elements[3]);
                emp.hashCode();
                duplicateCount.put(emp, duplicateCount.getOrDefault(emp, 0) + 1);
            });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // AUTHOR'S NOTE:
        // I modified the printing call to leverage the `forEach` method available on maps;
        // I find this to be a slightly more readable and intuitive way of iterating through Map key/value pairs
        // rather than returning a handle to the entrySet.
        duplicateCount.forEach((emp, count) -> {
            System.out.println(emp.firstName + " " + emp.middleInitial + " " + emp.lastName + ": " + count);
        });
    }
}
