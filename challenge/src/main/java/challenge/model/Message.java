package challenge.model;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;


        //Message extends ResourceSupport
public class Message {

    private String text;
    @NotNull
    private int userID;

    public int getUserID(){
        return this.userID;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setUserID(int id){
        this.userID = id;
    }
}
