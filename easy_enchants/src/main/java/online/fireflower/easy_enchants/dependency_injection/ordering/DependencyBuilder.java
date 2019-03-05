package online.fireflower.easy_enchants.dependency_injection.ordering;

import java.util.LinkedList;
import java.util.List;

public class DependencyBuilder {

    private List<OrderedRunnable> runnables = new LinkedList<>();

    public void addTask(Runnable runnable, int priority){
        runnables.add(new OrderedRunnable(runnable, priority));
    }

    public void runTasks(){

        runnables.sort(new OrderedRunnableComparator());
        for (OrderedRunnable runnable : runnables)
            runnable.runnable.run();

    }

}
