package infotecs.test.storage.service;

import infotecs.test.storage.model.Message;
import java.util.List;

//This is interface for service. The service implements the processing logic.
public interface MessageService {

    //Returns all messages in repository.
    List<Message> readAll();

    //Returns the message to which the specified id is mapped.
    Message get(String id);

    //Associates the specified message with the specified id in repository.
    boolean set(String id, Message message);

    //Removes the mapping for the specified id from repository if present. Returns removed message.
    Message remove(String id);

    //Saves the current state of the repository and returns it as a downloadable txt file.
    boolean dump(String filename);

    //Loads the state of the repository from a txt file.
    boolean load(String filename);

    //Removes all the messages from repository.
    void clear();

    //Delete messages with expired ttl.
    void ttlRemove();
}
