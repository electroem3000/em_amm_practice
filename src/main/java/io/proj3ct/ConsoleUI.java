package io.proj3ct;

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
        // Загрузка заданий из файла
        manager.getTasks().addAll(storage.load());
        while (true) {
            System.out.println("\nTodo List:");
            for (Task t : manager.getTasks()) {
                System.out.println(t);
            }
            System.out.println("\nMenu:   " +
                    "\n1) Add" +
                    "\n2) Complete" +
                    "\n3) Remove" +
                    "\n4) Save & Exit");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
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
                    storage.save(manager.getTasks());
                    System.out.println("Saved. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
