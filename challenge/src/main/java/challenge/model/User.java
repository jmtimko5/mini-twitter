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

    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(o instanceof User){
            User other = (User) o;
            return (this.getId() == other.getId() && this.getHandle().equals(other.getHandle()) && this.getName().equals(other.getName()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getHandle().hashCode();
    }

}
