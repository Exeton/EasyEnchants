package online.fireflower.easy_enchants.dependency_injection.ordering;

public class OrderedRunnable {

    public Runnable runnable;
    public int priority;

    public OrderedRunnable(Runnable runnable, int priority){
        this.runnable = runnable;
        this.priority = priority;
    }
}
