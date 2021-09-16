package infotecs.test.storage.repository;

import infotecs.test.storage.model.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//This class implements keeping messages in HashMap.
@Repository
public class MessageRepository {

    private static final Map<String, Message> messageMap = new HashMap<>();

    // Returns the message to which the specified id is mapped.
    public Message get(String id) {
        return messageMap.get(id);
    }

    //Associates the specified message with the specified id in this map.
    public void set(String id, Message mes) {
        messageMap.put(id, mes);
    }

    //Returns a ArrayList of the messages contained in this map.
    public ArrayList<Message> returnValues() {
        return new ArrayList<Message>(messageMap.values());
    }

    //Returns true if this map contains a mapping for the specified id.
    public boolean containsKey(String id){
        return messageMap.containsKey(id);
    }

    //Removes the mapping for the specified id from this map if present.
    public Message remove(String id) {
        return messageMap.remove(id);
    }

    //Removes all the mappings from this map.
    public void clear() {
        messageMap.clear();
    }
}
