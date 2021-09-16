package infotecs.test.storage.controller;

import infotecs.test.storage.model.Message;
import infotecs.test.storage.service.MessageServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//This is class for testing methods in class MessageController.
class MessageControllerTest {
    MessageServiceImpl service = new MessageServiceImpl();
    MessageController controller = new MessageController(service);

    @Test
    void set() {
        Message mes = new Message("M1", "Hello, world!", 30);
        controller.set(mes);
        assertNotNull(service.get("M1"));
        service.remove("M1");
    }

    @Test
    void get() {
        Message mes = new Message("M1", "Hello, world!", 30);
        controller.set(mes);
        assertNotNull(controller.get("M1"));
        Message tested = controller.get("M1").getBody();
        assertEquals("M1", tested.getId());
        assertEquals("Hello, world!", tested.getMessage());
        assertEquals(30, tested.getTtl());
        service.remove("M1");
    }

    @Test
    void remove() {
        Message mes = new Message("M1", "Hello, world!", 30);
        controller.set(mes);
        controller.remove("M1");
        assertNull(service.get("M1"));
    }

    @Test
    void dump() {
        Message mes = new Message("M1", "Hello, world!", 30);
        Message mesSecond = new Message("M2", "Good morning", 40);
        controller.set(mes);
        controller.set(mesSecond);
        controller.dump("test.txt");
        service.clear();
        controller.load("test.txt");

        Message tested = controller.get("M1").getBody();
        assertEquals("M1", tested.getId());
        assertEquals("Hello, world!", tested.getMessage());
        assertEquals(30, tested.getTtl());

        Message testedSecond = controller.get("M2").getBody();
        assertEquals("M2", testedSecond.getId());
        assertEquals("Good morning", testedSecond.getMessage());
        assertEquals(40, testedSecond.getTtl());
    }
}