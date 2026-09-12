package taskmanager;

public class Task {
    private String title;
    private boolean completed;

    private final int id;
    static int nextId = 1;

    public int getId() {
        return id;
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("任务标题不能为空");
        }
    }

    public Task(int id, boolean completed, String title) {
        validateTitle(title);
        this.id = id;
        if (id >= nextId) {
            nextId = id + 1;
        }
        this.completed = completed;
        this.title = title;
    }
    public Task(String title) {
        validateTitle(title);
        this.title = title;
        this.completed = false;
        id=nextId;
        nextId++;
    }

    public String getTitle() {
        return title;
    }

    public void markCompleted() {
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "title=" + title + "-------" + "完成状态=" + completed + "---------" + "ID:" + id;
    }
}
