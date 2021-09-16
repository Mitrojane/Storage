package infotecs.test.storage.model;

//This class is for keeping messages.
public class Message {

    private String id;
    private String message;
    private long ttl; //lifespan of message in seconds

    //the default ttl is 60 seconds
    public Message(String id, String mes, Integer ttl) {
        this.id = id;
        this.message = mes;
        if (ttl == null)
            this.ttl = 60;
        else {
            this.ttl = ttl;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mes) {
        this.message = mes;
    }

    public long getTtl() {
        return this.ttl;
    }
}
