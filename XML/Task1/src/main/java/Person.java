import java.util.List;

public class Person {
    String id = null;
    String firstName = null;
    String lastName = null;
    Gender gender = null;
    Spouce spouse = null;
    List<Parent> parents;
    List<Child> children;
    List<Sibling> siblings;

    Integer childrenNumber = null;
    Integer siblingsNumber = null;

    boolean validate() {
        if (childrenNumber != null && children.size() != childrenNumber) return false;
        if (siblingsNumber != null && siblings.size() != siblingsNumber) return false;

        return true;
    }
}
