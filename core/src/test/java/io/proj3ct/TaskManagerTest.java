package io.proj3ct;

import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    private final TaskManager tm = new TaskManager();;

    @Test
    void addAndGetTasks() {
        tm.addTask("Test");
        List<Task> tasks = tm.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test", tasks.get(0).getDescription());
    }

    @Test
    void removeTaskSuccess() {
        int id = tm.addTask("X");
        assertTrue(tm.removeTask(id));
        assertTrue(tm.getTasks().isEmpty());
    }

    @Test
    void removeTaskFail() {
        assertFalse(tm.removeTask(-1));
    }

    @Test
    void findByIdExisting() {
        int id = tm.addTask("A");
        Task t = tm.findById(id);
        assertNotNull(t);
        assertEquals("A", t.getDescription());
    }

    @Test
    void findByIdNonExisting() {
        assertNull(tm.findById(-1));
    }

    @Test
    void completeTaskSuccess() {
        int id = tm.addTask("Y");
        assertTrue(tm.completeTask(id));
        assertTrue(tm.findById(id).isCompleted());
    }

    @Test
    void completeTaskFail() {
        assertFalse(tm.completeTask(-1));
    }
}
