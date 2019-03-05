package online.fireflower.easy_enchants.dependency_injection.ordering;

import java.util.Comparator;

public class OrderedRunnableComparator implements Comparator<OrderedRunnable>{
    @Override
    public int compare(OrderedRunnable a, OrderedRunnable b) {
        return a.priority - b.priority;
    }
}
