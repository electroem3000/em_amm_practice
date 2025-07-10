package io.proj3ct;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(String description) {
        tasks.add(new Task(description));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task findById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public boolean removeTask(int id) {
        Task t = findById(id);
        if (t != null) {
            tasks.remove(t);
            return true;
        }
        return false;
    }

    public boolean completeTask(int id) {
        Task t = findById(id);
        if (t != null) {
            t.setCompleted(true);
            return true;
        }
        return false;
    }
}
