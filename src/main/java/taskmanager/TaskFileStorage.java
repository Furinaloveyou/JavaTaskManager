package taskmanager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;



public class TaskFileStorage {

    private final File file;
    public TaskFileStorage(File file) {
        this.file = file;
    }

    //存数据
    public void save(List<Task> tasks) throws IOException {
        Path target = file.toPath().toAbsolutePath();
        Path temp = Files.createTempFile(target.getParent(), "tasks-", ".tmp");
        try {
            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(temp.toFile()))) {
                for (Task task : tasks) {
                    String taskLine = task.getId() + "|"
                            + task.isCompleted() + "|" + task.getTitle();
                    writer.write(taskLine);
                    writer.newLine();
                }
            }
            // 内层 try 结束，writer 已关闭
            Files.move(temp, target, StandardCopyOption.ATOMIC_MOVE);
        } finally {
            Files.deleteIfExists(temp);
        }
    }

    //取数据
    private List<String> loadLines() throws IOException {
        List<String> lines = new ArrayList<>();
        if (!file.exists()) {
            return lines;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }
        return lines;
    }

    //载入数据
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        List<String> lines = loadLines();
        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split("\\|", 3);
            if (parts.length != 3) {
                throw new IOException("任务文件第 " + lineNumber + " 行格式错误");
            }
            int id;

            try {
                id = Integer.parseInt(parts[0]);
                if (id <= 0) {
                    throw new IOException(
                            "任务文件第 " + lineNumber + " 行的 ID 必须大于 0"
                    );
                }
            } catch (NumberFormatException e) {
                throw new IOException("任务文件第 " + lineNumber + " 行的 ID 不是整数", e);
            }
            String completedText = parts[1];
            if (!completedText.equals("true") && !completedText.equals("false")) {
                throw new IOException("任务文件第 " + lineNumber + " 行的完成状态必须为 true 或 false");
            }

            boolean completed = Boolean.parseBoolean(completedText);

            String title = parts[2];

            if (title.isBlank()) {
                throw new IOException("任务文件第 " + lineNumber + " 行的标题不能为空");
            }
            Task task = new Task(id, completed, title);
            tasks.add(task);
        }
        return tasks;
    }

}
