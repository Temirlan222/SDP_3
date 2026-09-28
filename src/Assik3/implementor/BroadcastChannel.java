package Assik3.implementor;

public interface BroadcastChannel {
    default String name() {
        return getClass().getSimpleName();
    }

    void publish(String destination, String content);
}
