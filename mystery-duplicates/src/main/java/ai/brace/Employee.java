package ai.brace;

import java.util.Objects;

public class Employee
{
    // AUTHOR'S NOTE:
    // The reason the task was originally failing is
    // because no overriden implementation of `hashCode()` exists;
    // therefore, the JVM derives a hashcode from the object instance's address in memory.
    // This means that even if you have two instances of Employee whose fields are equal,
    // storing both in a map will result in two distinct entries in that map.

    // The solution is to implement a `hashCode` method that
    // takes all of the field values into account.

    public String firstName;
    public String middleInitial;
    public String lastName;
    public String socialSecurityNumber;

    public Employee(String _lastName, String _firstName, String _middleInitial, String _socialSecurityNumber)
    {
        firstName = _firstName;
        middleInitial = _middleInitial;
        lastName = _lastName;
        socialSecurityNumber = _socialSecurityNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) &&
                Objects.equals(middleInitial, employee.middleInitial) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(socialSecurityNumber, employee.socialSecurityNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleInitial, lastName, socialSecurityNumber);
    }
}
