import java.util.*;

class Task {
    String name;
    int duration;
    int earliestStartTime = 0;
    int latestFinishTime = Integer.MAX_VALUE;
    List<Task> dependencies = new ArrayList<>();

    public Task(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public void addDependency(Task task) {
        dependencies.add(task);
    }
}

class WorkflowManager {
    List<Task> tasks;

    public WorkflowManager(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void calculateEarliestFinishTimes() {
        for (Task task : tasks) {
            for (Task dependency : task.dependencies) {
                task.earliestStartTime = Math.max(task.earliestStartTime, dependency.earliestStartTime + dependency.duration);
            }
        }
    }

    public void calculateLatestFinishTimes() {
        for (int i = tasks.size() - 1; i >= 0; i--) {
            Task task = tasks.get(i);
            for (Task dependency : task.dependencies) {
                dependency.latestFinishTime = Math.min(dependency.latestFinishTime, task.latestFinishTime - task.duration);
            }
        }
    }

    public int getEarliestCompletionTime() {
        int maxFinishTime = 0;
        for (Task task : tasks) {
            maxFinishTime = Math.max(maxFinishTime, task.earliestStartTime + task.duration);
        }
        return maxFinishTime;
    }

    public int getLatestCompletionTime() {
        int minFinishTime = Integer.MAX_VALUE;
        for (Task task : tasks) {
            minFinishTime = Math.min(minFinishTime, task.latestFinishTime);
        }
        return minFinishTime;
    }
}

public class ProjectScheduler {
    public static void main(String[] args) {
        // Create tasks
        Task taskA = new Task("A", 3);
        Task taskB = new Task("B", 2);
        Task taskC = new Task("C", 4);
        Task taskD = new Task("D", 1);

        // Define dependencies
        taskB.addDependency(taskA);
        taskC.addDependency(taskA);
        taskD.addDependency(taskB);
        taskD.addDependency(taskC);

        // Create a list of tasks
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskA);
        tasks.add(taskB);
        tasks.add(taskC);
        tasks.add(taskD);

        // Create Workflow Manager
        WorkflowManager manager = new WorkflowManager(tasks);

        // Calculate earliest and latest finish times
        manager.calculateEarliestFinishTimes();
        manager.calculateLatestFinishTimes();

        // Output results
        System.out.println("Earliest Completion Time: " + manager.getEarliestCompletionTime());
        System.out.println("Latest Completion Time: " + manager.getLatestCompletionTime());
    }
}
