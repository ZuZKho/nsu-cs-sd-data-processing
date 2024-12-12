public class Child {
    String id;
    ChildType childType = null;

    Child(String _id, ChildType _cht) {
        this.id = _id;
        this.childType = _cht;
    }

    enum ChildType {
        Daughter,
        Son
    }
}
