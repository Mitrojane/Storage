package infotecs.test.storage.controller;

import infotecs.test.storage.model.Message;
import infotecs.test.storage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//This class implements controller. Controller accepts input and converts it to commands for the service.
@RestController
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /*Accepts input as JSON and converts to object of Message.
    Then call function "set" of MessageService.
    Returns status "CREATED".*/
    @PostMapping(value = "/messages")
    public ResponseEntity<?> set(@RequestBody Message message) {
        messageService.set(message.getId(), message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*Accepts nothing. Then call function "readAll" of MessageService.
    Returns all Messages in repository and status "OK" or status "NOT_FOUND".*/
    @GetMapping(value = "/messages")
    public ResponseEntity<List<Message>> read() {
        final List<Message> messages = messageService.readAll();
        if (messages != null && !messages.isEmpty())
            return new ResponseEntity<>(messages, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*Accepts id in URI. Then call function "get" of MessageService.
    Returns the message to which the specified id is mapped and status "OK" or status "NOT_FOUND". */
    @GetMapping(value = "/messages/{id}")
    public ResponseEntity<Message> get(@PathVariable(name = "id") String id) {
        final Message message = messageService.get(id);
        if (message != null)
            return new ResponseEntity<>(message, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*Accepts id in URI. Then call function "remove" of MessageService.
    Returns removed message and status "OK" or status "NO_CONTENT". */
    @DeleteMapping(value = "/messages/{id}")
    public ResponseEntity<?> remove(@PathVariable(name = "id") String id) {
        final Message deleted = messageService.remove(id);
        if (deleted != null)
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*Accepts filename (txt) in URI. Then call function "load" of MessageService.
    Loads the state of the repository from a txt file.
    Returns status "OK" or status "NOT_FOUND". */
    @PostMapping("/load/{filename:.+}")
    public ResponseEntity<?> load(@PathVariable("filename") String filename)  {
        String[] splitted = filename.split("\\.");
        if (!splitted[splitted.length-1].equals("txt")) {
            return new ResponseEntity("Error: wrong file format", HttpStatus.BAD_REQUEST);
        }
        boolean ok = messageService.load(filename);
        if (ok)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*Accepts filename (txt) in URI. Then call function "dump" of MessageService.
    Saves the current state of the repository and returns it as a downloadable txt file.
    Returns status "OK" or status "NOT_FOUND". */
    @GetMapping("/dump/{filename:.+}")
    public ResponseEntity<Resource> dump(@PathVariable("filename") String filename) {
        String[] splitted = filename.split("\\.");
        if (!splitted[splitted.length-1].equals("txt")) {
            return new ResponseEntity("Error: wrong file format", HttpStatus.BAD_REQUEST);
        }
        boolean ok = messageService.dump(filename);
        if (ok)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}