package challenge.model;

public class User {

    private int id;
    private String handle;
    private String name;
    private String password;

    public User(int id, String handle, String name, String password) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.password = password;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
