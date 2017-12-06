package challenge.model;


//Message extends ResourceSupport
public class Message {

    private int Id;
    private int authorId;
    private String content;

    public Message(int id, int authorId, String content) {
        Id = id;
        this.authorId = authorId;
        this.content = content;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int author_id) {
        this.authorId = author_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
