package taskmanager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class TaskManager {
    private Map<Integer, Task> taskMap = new LinkedHashMap<>();

    public void addTask(String title) {
        Task task = new Task(title);
        taskMap.put(task.getId(), task);
    }

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public int getTaskCount() {
        return taskMap.size();
    }

    public Task getTask(int id) {
        return taskMap.get(id);
    }

    public boolean markTaskCompleted(Integer id) {
        if (taskMap.get(id) != null) {
            taskMap.get(id).markCompleted();
            return true;
        }
        return false;
    }

    public boolean deleteTask(Integer id) {
        if (taskMap.get(id) != null) {
            taskMap.remove(id);
            return true;
        }
        return false;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    public int deleteCompletedTasks() {
        int deleteCount = 0;
        Iterator<Task> iterator = taskMap.values().iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();
            if(task.isCompleted()){
                iterator.remove();
                deleteCount++;
            }

        }
        return deleteCount;
    }

    public List<Task> searchTasks(String keyword) {
        return taskMap.values()
                .stream()
                .filter(task -> task.getTitle().contains(keyword))
                .toList();
    }


}
