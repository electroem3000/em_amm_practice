package io.proj3ct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {

    @TempDir
    Path tempDir;
    private FileStorage storage;
    private Path filePath;

    @BeforeEach
    void setUp() {
        filePath = tempDir.resolve("tasks.txt");
        storage = new FileStorage(filePath.toString());
    }

    @Test
    void loadWhenFileNotExists() {
        List<Task> tasks = storage.load();
        assertNotNull(tasks, "Список не должен быть null");
        assertTrue(tasks.isEmpty(), "Список должен быть пустым, если файла нет");
    }

    @Test
    void saveAndLoadTasks() {
        Task t1 = new Task(1, "Task One", false);
        Task t2 = new Task(2, "Task Two", true);
        List<Task> original = List.of(t1, t2);

        storage.save(original);
        List<Task> loaded = storage.load();

        assertEquals(original.size(), loaded.size(), "Размер списков должен совпадать");

        Task l1 = loaded.get(0);
        assertEquals(1, l1.getId());
        assertEquals("Task One", l1.getDescription());
        assertFalse(l1.isCompleted());

        Task l2 = loaded.get(1);
        assertEquals(2, l2.getId());
        assertEquals("Task Two", l2.getDescription());
        assertTrue(l2.isCompleted());
    }

    @Test
    void loadIgnoresInvalidLines() throws IOException {
        String content = ""
            + "bad_line_without_delimiters\n"
            + "3|Valid Task|false\n"
            + "4|Too|Many|Fields|true\n"
            + "\n";
        Files.writeString(filePath, content);

        List<Task> tasks = storage.load();
        assertEquals(1, tasks.size(), "Должна быть ровно одна валидная запись");

        Task only = tasks.get(0);
        assertEquals(3, only.getId());
        assertEquals("Valid Task", only.getDescription());
        assertFalse(only.isCompleted());
    }
}
