package io.proj3ct;

/*
Приложение для управления списком дел. Вы можете:
Просмотреть все задачи.
Добавить новую задачу (пункт 1).
Отметить задачу как выполненную (пункт 2).
Удалить задачу (пункт 3).
Сохранить изменения и выйти (пункт 4) — все данные сохранятся в файл и загрузятся при следующем запуске.
*/
public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        FileStorage storage = new FileStorage("tasks.txt");
        ConsoleUI ui = new ConsoleUI(manager, storage);
        ui.start();
    }
}