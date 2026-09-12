package taskmanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskFileStorageTest {

    @TempDir
    File tempDir;

    @Test
    void savedTasksCanBeLoaded() throws IOException {
        File taskFile = new File(tempDir, "tasks.txt");
        TaskFileStorage storage = new TaskFileStorage(taskFile);
        List<Task> taskList = new ArrayList<>();
        Task task = new Task("却笑人间举子忙");
        task.markCompleted();
        taskList.add(task);
        storage.save(taskList);
        List<Task> taskList1=storage.load();
        assertEquals(1,taskList1.size());
        assertEquals(taskList.getFirst().getId(),taskList1.getFirst().getId());
        assertEquals(taskList.getFirst().getTitle(),taskList1.getFirst().getTitle());
        assertEquals(taskList.getFirst().isCompleted(),taskList1.getFirst().isCompleted());
        //写的有点shi了
    }
    @Test
    void loadingInvalidIdThrowsIOException() throws IOException {
        File taskFile = new File("tasks.txt");
        Files.writeString(taskFile.toPath(), "abc|false|错误任务");
        TaskFileStorage storage = new TaskFileStorage(taskFile);
        IOException exception = assertThrows(
                IOException.class,
                storage::load
        );
        assertTrue(exception.getMessage().contains("ID 不是整数"));
    }

}
