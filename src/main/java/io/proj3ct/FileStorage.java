package io.proj3ct;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private String filename;

    public FileStorage(String filename) {
        this.filename = filename;
    }

    public void save(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task t : tasks) {
                writer.write(String.format("%d|%s|%b%n", t.getId(), t.getDescription(), t.isCompleted()));
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return tasks;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String desc = parts[1];
                    boolean comp = Boolean.parseBoolean(parts[2]);
                    tasks.add(new Task(id, desc, comp));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}
