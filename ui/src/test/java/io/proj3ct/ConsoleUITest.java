package io.proj3ct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleUITest {

    @Mock
    private TaskManager manager;

    @Mock
    private FileStorage storage;

    private ConsoleUI ui;

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void whenChooseSave_thenLoadGetTasksTwiceSaveOnceAndExitMessage() {
        List<Task> tasks = new ArrayList<>();
        when(storage.load()).thenReturn(tasks);
        when(manager.getTasks()).thenReturn(tasks);

        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ui = new ConsoleUI(manager, storage);
        ui.start();

        verify(storage, times(1)).load();
        verify(manager, times(2)).getTasks();
        verify(storage, times(1)).save(tasks);
        verifyNoMoreInteractions(storage, manager);

        String output = outContent.toString();
        assertTrue(output.contains("Menu:"), "Меню должно выводиться");
        assertTrue(output.contains("Save & Exit"), "Должно быть предложение сохранить и выйти");
        assertTrue(output.contains("Saved. Goodbye!"), "Должно отобразиться подтверждение выхода");
    }

    @Test
    void whenAddTask_thenLoadGetTasksTwiceAddOnceSaveOnceAndConfirmation() {
        List<Task> tasks = new ArrayList<>();
        when(storage.load()).thenReturn(tasks);
        when(manager.getTasks()).thenReturn(tasks);

        System.setIn(new ByteArrayInputStream("1\nNew task\n0\n".getBytes()));
        ui = new ConsoleUI(manager, storage);
        ui.start();

        verify(storage, times(1)).load();
        verify(manager, times(2)).getTasks();
        verify(manager, times(1)).addTask("New task");
        verify(storage, times(1)).save(tasks);
        verifyNoMoreInteractions(storage, manager);
    }
//
//    @Test
//    void whenRemoveTaskNotFound_thenPrintNotFoundAndSave() {
//        List<Task> tasks = new ArrayList<>();
//        when(storage.load()).thenReturn(tasks);
//        when(manager.getTasks()).thenReturn(tasks);
//        when(manager.removeTask(3)).thenReturn(false);
//
//        System.setIn(new ByteArrayInputStream("3\n3\n0\n".getBytes()));
//        ui = new ConsoleUI(manager, storage);
//        ui.start();
//
//        verify(manager, times(1)).removeTask(3);
//        assertTrue(outContent.toString().contains("Not found!"));
//        verify(storage, times(1)).save(tasks);
//    }
//
//    @Test
//    void whenRemoveTaskSuccess_thenNoErrorAndSave() {
//        List<Task> tasks = new ArrayList<>();
//        when(storage.load()).thenReturn(tasks);
//        when(manager.getTasks()).thenReturn(tasks);
//        when(manager.removeTask(4)).thenReturn(true);
//
//        System.setIn(new ByteArrayInputStream("3\n4\n0\n".getBytes()));
//        ui = new ConsoleUI(manager, storage);
//        ui.start();
//
//        verify(manager, times(1)).removeTask(4);
//        assertFalse(outContent.toString().contains("Not found!"));
//        verify(storage, times(1)).save(tasks);
//    }
//
//
//    @Test
//    void whenCompleteTaskNotFound_thenPrintNotFoundAndSave() {
//        List<Task> tasks = new ArrayList<>();
//        when(storage.load()).thenReturn(tasks);
//        when(manager.getTasks()).thenReturn(tasks);
//        when(manager.completeTask(5)).thenReturn(false);
//
//        System.setIn(new ByteArrayInputStream("2\n5\n0\n".getBytes()));
//        ui = new ConsoleUI(manager, storage);
//        ui.start();
//
//        verify(manager, times(1)).completeTask(5);
//        assertTrue(outContent.toString().contains("Not found!"));
//        verify(storage, times(1)).save(tasks);
//    }
//
//    @Test
//    void whenMultipleAddTasks_thenAddCalledTwiceAndSave() {
//        List<Task> tasks = new ArrayList<>();
//        when(storage.load()).thenReturn(tasks);
//        when(manager.getTasks()).thenReturn(tasks);
//
//        System.setIn(new ByteArrayInputStream("1\nT1\n1\nT2\n0\n".getBytes()));
//        ui = new ConsoleUI(manager, storage);
//        ui.start();
//
//        verify(manager, times(1)).addTask("T1");
//        verify(manager, times(1)).addTask("T2");
//        verify(storage, times(1)).save(tasks);
//    }
}
