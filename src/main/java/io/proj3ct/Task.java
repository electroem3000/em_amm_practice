package io.proj3ct;

public class Task {
    private static int counter = 0;
    private int id;
    private String description;
    private boolean completed;

    // Конструктор для нового задания
    public Task(String description) {
        this.id = ++counter;
        this.description = description;
        this.completed = false;
    }

    // Конструктор для загрузки задания из файла
    public Task(int id, String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        if (id > counter) {
            counter = id;
        }
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s)", id, description, completed ? "Completed" : "Pending");
    }
}
