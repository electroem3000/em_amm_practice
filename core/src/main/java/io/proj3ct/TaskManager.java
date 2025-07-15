package io.proj3ct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static final Logger logger = LogManager.getLogger(TaskManager.class);

    private List<Task> tasks = new ArrayList<>();

    public void addTask(String description) {
        logger.debug("Вход в addTask() с описанием задачи: {}", description);
        try {
            Task t = new Task(description);
            tasks.add(t);
            logger.info("Задача добавлена успешно: [{}] {}", t.getId(), description);
        } catch (Exception ex) {
            logger.error("Не удалось добавить задачу: {}", description, ex);
        }
    }

    public List<Task> getTasks() {
        logger.debug("Вход в getTasks(), текущее количество задач: {}", tasks.size());
        return new ArrayList<>(tasks);
    }

    public Task findById(int id) {
        logger.debug("Вход в findById() с id = {}", id);
        for (Task t : tasks) {
            if (t.getId() == id) {
                logger.info("Найдена задача: [{}] {}", id, t.getDescription());
                return t;
            }
        }
        logger.warn("Задача с id = {} не найдена", id);
        return null;
    }

    public boolean removeTask(int id) {
        logger.debug("Вход в removeTask() с id = {}", id);
        Task t = findById(id);
        if (t != null) {
            tasks.remove(t);
            logger.info("Задача удалена: [{}] {}", id, t.getDescription());
            return true;
        }
        logger.error("Не удалось удалить задачу: задача с id = {} отсутствует", id);
        return false;
    }

    public boolean completeTask(int id) {
        logger.debug("Вход в completeTask() с id = {}", id);
        Task t = findById(id);
        if (t != null) {
            t.setCompleted(true);
            logger.info("Задача помечена как выполненная: [{}] {}", id, t.getDescription());
            return true;
        }
        logger.error("Не удалось отметить задачу как выполненную: id = {} не найден", id);
        return false;
    }
}
