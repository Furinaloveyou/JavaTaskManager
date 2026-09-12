package taskmanager;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        TaskFileStorage taskFileStorage = new TaskFileStorage(new File("tasks.txt"));

        try {
            for (Task task : taskFileStorage.load()) {
                taskManager.addTask(task);
            }
            System.out.println(
                    "成功加载 " + taskManager.getTaskCount() + " 条任务"
            );
        } catch (IOException e) {
            System.out.println("任务加载失败：" + e.getMessage());
            System.out.println("为避免覆盖原数据，程序已停止");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. 添加任务\n" +
                    "2. 查看任务\n" +
                    "3. 标记任务完成\n" +
                    "4. 删除任务\n" +
                    "5.删除已完成任务\n"+
                    "6.查找关键词\n"+
                    "0. 退出\n" +
                    "请选择：");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.println("请输入任务标题:");
                String title = scanner.nextLine();
                try {
                    taskManager.addTask(title);
                    System.out.println("任务添加成功");
                    saveTasks(taskManager, taskFileStorage);
                } catch (IllegalArgumentException e) {
                    System.out.println("添加失败！");
                    System.out.println(e.getMessage());
                }
            } else if (choice.equals("2")) {
                if (taskManager.getTaskCount() == 0) {
                    System.out.println("暂无任务");
                } else {
                    for (Task task : taskManager.getAllTasks()) {
                        System.out.println(task);
                    }
                }
            } else if (choice.equals("3")) {
                System.out.println("请输入任务编号：");
                String taskNumberText = scanner.nextLine();
                try {
                    int taskId = Integer.parseInt(taskNumberText);
                    if (taskManager.markTaskCompleted(taskId)) {
                        System.out.println("已完成任务！");
                        saveTasks(taskManager, taskFileStorage);
                    } else {
                        System.out.println("任务编号不存在");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("任务编号必须为整数");
                }
            } else if (choice.equals("4")) {
                System.out.println("请输入要删除的任务编号：");
                String taskDeleteNumberText = scanner.nextLine();
                try {
                    int taskId = Integer.parseInt(taskDeleteNumberText);
                    if (taskManager.deleteTask(taskId)) {
                        System.out.println("任务已删除！");
                        saveTasks(taskManager, taskFileStorage);
                    } else {
                        System.out.println("任务编号不存在");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("任务编号必须为整数");
                }
            }
            else if (choice.equals("5")){
                int deleteNum= taskManager.deleteCompletedTasks();
                if(deleteNum==0){
                    System.out.println("没有已完成的任务");
                }
                else{
                    System.out.println("已删除"+deleteNum+"个已完成任务");
                    saveTasks(taskManager, taskFileStorage);
                }
            }
            else if(choice.equals("6")){
                System.out.println("请输入关键词：");
                String keyword = scanner.nextLine();
                List<Task> results = taskManager.searchTasks(keyword);
                if(results.isEmpty()){
                    System.out.println("没有找到匹配任务");
                }
                else{
                    for(Task task:results){
                        System.out.println(task);
                    }
                }
            }
            else if (choice.equals("0")) {
                saveTasks(taskManager, taskFileStorage);
                System.out.println("已退出");
                break;
            } else {
                System.out.println("无效选项");
            }
        }
        scanner.close();
    }
    private static void saveTasks(
            TaskManager taskManager,
            TaskFileStorage taskFileStorage
    ) {
        try {
            taskFileStorage.save(taskManager.getAllTasks());
        } catch (IOException e) {
            System.out.println("自动保存失败：" + e.getMessage());
        }
    }
}
