package challenge.model;

public class User {

    private int Id;
    private String Handle;
    private String Name;

    public User(int id, String handle, String name) {
        Id = id;
        Handle = handle;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getHandle() {
        return Handle;
    }

    public void setHandle(String handle) {
        Handle = handle;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
