package infotecs.test.storage.service;

import infotecs.test.storage.model.Message;
import infotecs.test.storage.repository.MessageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

//Implementation of interface MessageService.
@Service
public class MessageServiceImpl implements MessageService {

    private static final Map<Long, ArrayList<String>> queueToRemove = new HashMap<>();
    private static final MessageRepository messageRepository = new MessageRepository();
    private static Logger log = Logger.getLogger(MessageServiceImpl.class.getName());

    @Override
    public ArrayList<Message> readAll() {
        log.info("Get all messages.");
        return messageRepository.returnValues();
    }

    @Override
    public Message get(String id) {
        log.info("Get message with id " + id);
        if (messageRepository.containsKey(id))
            return messageRepository.get(id);
        else return null;
    }

    @Override
    public boolean set(String id, Message message) {
        long curTime = System.currentTimeMillis()/1000;
        long dieTime = curTime + message.getTtl();
        //removes message id from the queue for deletion
        if (messageRepository.containsKey(id)) {
            for (Map.Entry<Long, ArrayList<String>> entry : queueToRemove.entrySet()) {
                if (entry.getValue().contains(id)) {
                    entry.getValue().remove(entry.getValue().indexOf(id));
                }
            }
        }
        //add a new message to the queue for deletion
        if (!queueToRemove.containsKey(dieTime)) {
            ArrayList<String> tmp = new ArrayList<String>();
            tmp.add(message.getId());
            queueToRemove.put(dieTime, tmp);
        } else {
            queueToRemove.get(dieTime).add(message.getId());
        }
        log.info("Set new message with id " + id);
        messageRepository.set(id, message);
        return true;
    }

    @Override
    public Message remove(String id) {
        log.info("Remove message with id " + id);
        return messageRepository.remove(id);
    }

    @Override
    public void clear() {
        log.info("Remove all messages.");
        messageRepository.clear();
    }

    @Override
    public boolean load(String filename) {
        try {
            FileReader reader= new FileReader(filename);
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
               String[] idCur = scan.nextLine().split(": ");
               String[] mesCur = scan.nextLine().split(": ");
               String[] ttlCur = scan.nextLine().split(": ");
               Message message = new Message(idCur[1], mesCur[1], Integer.parseInt(ttlCur[1]));
               this.set(message.getId(),message);
               scan.nextLine();
            }
            reader.close();
            log.info("Load file");
            return true;
        } catch (IOException e) {
            log.info("Could not load file");
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    @Override
    public boolean dump(String filename) {
        ArrayList<Message> list = readAll();
        StringBuilder strBuild = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            strBuild.append("id: " + list.get(i).getId() +
                    "\nMessage: " + list.get(i).getMessage() +
                    "\nttl: " + list.get(i).getTtl() + "\n\n");
        }
        String data = strBuild.toString();
        try(FileWriter writer = new FileWriter(filename)) {
            writer.write(data);
            writer.close();
            log.info("Data uploaded");
            return true;
        } catch(IOException e) {
            log.info("Could not upload data");
            throw new RuntimeException("Error:"+e.getMessage());
        }
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void ttlRemove() {
        long curTime = System.currentTimeMillis() /1000;
        if(queueToRemove.containsKey(curTime)) {
            ArrayList<String> tmp = queueToRemove.get(curTime);
            for (int i = 0; i < tmp.size(); i++) {
                messageRepository.remove(tmp.get(i));
            }
            log.info("Kill messages with id " + tmp.toString());
            queueToRemove.remove(curTime);
        }
    }
}
