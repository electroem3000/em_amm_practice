package io.proj3ct;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private TaskManager manager;
    private FileStorage storage;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleUI(TaskManager manager, FileStorage storage) {
        this.manager = manager;
        this.storage = storage;
    }

    public void start() {
        manager.setTasks(storage.load());
        while (true) {
            System.out.println("\nMenu:" +
                    "\n1) Add" +
                    "\n2) Complete" +
                    "\n3) Remove" +
                    "\n4) Show list" +
                    "\n5) Show pending" +
                    "\n0) Save & Exit");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "0":
                    storage.save(manager.getTasks());
                    System.out.println("Saved. Goodbye!");
                    return;
                case "1":
                    System.out.print("Enter description: ");
                    String desc = scanner.nextLine();
                    manager.addTask(desc);
                    break;
                case "2":
                    System.out.print("Enter task id to complete: ");
                    int cid = Integer.parseInt(scanner.nextLine());
                    if (!manager.completeTask(cid)) System.out.println("Not found!");
                    break;
                case "3":
                    System.out.print("Enter task id to remove: ");
                    int rid = Integer.parseInt(scanner.nextLine());
                    if (!manager.removeTask(rid)) System.out.println("Not found!");
                    break;
                case "4":
                    System.out.println("\nFull Todo List:");
                    for (Task t : manager.getTasks()) {
                        System.out.println(t);
                    }
                    break;
                case "5":
                    List<Task> tasks = manager.getTasks()
                            .stream()
                            .filter(task -> !task.isCompleted())
                            .toList();
                    System.out.println("Pending tasks:");
                    if (tasks.isEmpty()) {
                        System.out.println("  (список пуст)");
                    } else {
                        for (Task t : tasks) {
                            System.out.printf("  [%d] %-20s",
                                    t.getId(),
                                    t.getDescription());
                        }
                    }
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
