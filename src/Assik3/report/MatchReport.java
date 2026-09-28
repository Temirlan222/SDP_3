package Assik3.report;

import Assik3.implementor.BroadcastChannel;

public abstract class MatchReport {
    private final BroadcastChannel channel;

    protected MatchReport(BroadcastChannel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("Канал не задан");
        }
        this.channel = channel;
    }

    protected abstract String buildContent();

    public final void publish(String destination) {
        channel.publish(destination, buildContent());
    }
}
