import java.util.ArrayList;
import java.util.List;

public class PersonLibrary {

    private final ArrayList<Person> persons = new ArrayList<>();

    private Person currentPerson = null;

    public List<Person> getPeople() {
        return persons;
    }

    public void startNewPerson() {
        currentPerson = new Person();
    }

    public void mergePerson() {
        // merge...


        currentPerson = null;
    }

    public void setGender(String gender) {
        gender = gender.trim().toLowerCase();
        if (gender.equals("m") || gender.equals("male")) {
            assert currentPerson.gender != Gender.Female : "libGen";
            currentPerson.gender = Gender.Male;
        } else
        if (gender.equals("f") || gender.equals("female")) {
            assert currentPerson.gender != Gender.Male : "libGen";
            currentPerson.gender = Gender.Female;
        } else {
            assert false;
        }
    }

    public void setSiblingsNumber(String value) {
        assert currentPerson.siblingsNumber == null || currentPerson.siblingsNumber.equals(Integer.valueOf(value)) : "libSN";
        currentPerson.siblingsNumber = Integer.valueOf(value);
    }

    public void setChildrenNumber(String value) {
        assert currentPerson.childrenNumber == null || currentPerson.childrenNumber.equals(Integer.valueOf(value)) : "libCHN";
        currentPerson.childrenNumber = Integer.valueOf(value);
    }

    public void setFullName(String fullName) {
        String firstName = fullName.split(" ")[0];
        String lastName = fullName.split(" ")[1];
        assert(currentPerson.firstName == null || currentPerson.firstName.equals(firstName));
        assert(currentPerson.lastName == null || currentPerson.lastName.equals(lastName));

        currentPerson.firstName = firstName;
        currentPerson.lastName = lastName;
    }

    public void setId(String id) {
        assert(currentPerson.id == null || currentPerson.id.equals(id));

        currentPerson.id = id;
    }

    public void addChild(Child child) {
        if (currentPerson.children == null) {
            currentPerson.children = new ArrayList<>();
        }
        currentPerson.children.add(child);
    }
}
