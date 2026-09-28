package Assik3.report;

import Assik3.implementor.BroadcastChannel;

public class LiveMatchReport extends MatchReport {
    private final String teams;
    private final String score;
    private final String minute;
    private final String latestEvent;

    public LiveMatchReport(BroadcastChannel channel, String teams, String score,
                           String minute, String latestEvent) {
        super(channel);
        this.teams = teams;
        this.score = score;
        this.minute = minute;
        this.latestEvent = latestEvent;
    }

    @Override
    protected String buildContent() {
        return "Идёт матч: " + teams
                + "\nСчёт: " + score
                + "\nМинута: " + minute
                + "\nПоследнее событие: " + latestEvent;
    }
}
