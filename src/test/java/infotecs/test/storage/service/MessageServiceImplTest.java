package infotecs.test.storage.service;

import infotecs.test.storage.model.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//This is class for testing methods in class MessageService.
class MessageServiceImplTest {

    MessageServiceImpl service = new MessageServiceImpl();

    @Test
    void get() {
        Message mes = new Message("M1", "Hello, world!", 30);
        service.set("M1", mes);
        Message tested = service.get("M1");
        assertEquals(mes, tested);
        service.remove("M1");
    }

    @Test
    void set() {
        Message mes = new Message("M1", "Hello, world!", 30);
        service.set("M1", mes);
        Message tested = service.get("M1");
        assertEquals("M1", tested.getId());
        assertEquals("Hello, world!", tested.getMessage());
        assertEquals(30, tested.getTtl());
        service.remove("M1");
    }

    @Test
    void remove() {
        Message mes = new Message("M1", "Hello, world!", 30);
        service.set("M1", mes);
        service.remove("M1");
        assertNull(service.get("M1"));
    }

    @Test
    void load() {
        Message mes = new Message("M1", "Hello, world!", 30);
        Message mesSecond = new Message("M2", "Good morning", 40);
        service.set(mes.getId(), mes);
        service.set(mesSecond.getId(), mesSecond);
        service.dump("test.txt");
        service.clear();
        service.load("test.txt");

        Message tested = service.get("M1");
        assertEquals("M1", tested.getId());
        assertEquals("Hello, world!", tested.getMessage());
        assertEquals(30, tested.getTtl());

        Message testedSecond = service.get("M2");
        assertEquals("M2", testedSecond.getId());
        assertEquals("Good morning", testedSecond.getMessage());
        assertEquals(40, testedSecond.getTtl());

    }
}