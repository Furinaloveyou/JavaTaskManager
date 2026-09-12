package taskmanager;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    @Test
    void addingTaskIncreasesCount() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("学习Junit");
        assertEquals(1, taskManager.getTaskCount());
    }

    @Test
    void markingExistingTaskCompletesIt() {
        TaskManager taskManager = new TaskManager();
        Task task = new Task("Java");
        taskManager.addTask(task);
        boolean marked = taskManager.markTaskCompleted(task.getId());
        assertTrue(marked);
        assertTrue(task.isCompleted());
    }

    @Test
    void markingMissingTaskReturnsFalse() {
        TaskManager taskManager = new TaskManager();
        boolean marked = taskManager.markTaskCompleted(999);
        assertFalse(marked);
    }

    @Test
    void deletingExistingTaskRemovesTask() {
        TaskManager taskManager = new TaskManager();
        Task task = new Task("Why not you");
        taskManager.addTask(task);
        boolean marked = taskManager.deleteTask(task.getId());
        assertTrue(marked);
        assertEquals(0, taskManager.getTaskCount());
    }

    @Test
    void deletingCompletedTasksRemovesOnlyCompletedTasks() {
        Task task1 = new Task("A");
        Task task2 = new Task("B");
        Task task3 = new Task("C");
        task1.markCompleted();
        ;
        task2.markCompleted();
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        int deleteNum = taskManager.deleteCompletedTasks();
        assertEquals(2, deleteNum);
        assertEquals(1, taskManager.getTaskCount());
        assertEquals("C", taskManager.getAllTasks().getFirst().getTitle());
    }
    @Test
    void searchingTasksReturnsMatchingTitles() {
        TaskManager taskManager = new TaskManager();
        List<Task> results = new ArrayList<>();
        taskManager.addTask(new Task("Java学习"));
        taskManager.addTask(new Task("Java项目"));
        taskManager.addTask(new Task("数据结构"));
        results = taskManager.searchTasks("Java");
        assertEquals(2, results.size());
        assertEquals("Java学习", results.getFirst().getTitle());
        assertEquals("Java项目", results.getLast().getTitle());
    }
    @Test
    void addingBlankTitleIsRejected(){
        TaskManager taskManager = new TaskManager();
        assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.addTask("   ")
        );
        assertEquals(0, taskManager.getTaskCount());
    }
}
