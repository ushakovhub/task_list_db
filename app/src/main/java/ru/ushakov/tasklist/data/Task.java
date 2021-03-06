package ru.ushakov.tasklist.data;

public class Task {
    private String taskName;
    private Integer taskID;

    // Конструктор для получения данных из базы
    public Task(String taskName, Integer taskID) {
        this.taskName = taskName;
        this.taskID = taskID;
    }

    // Конструктор для вставки данных в базу
    public Task(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskId) {
        this.taskID = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}