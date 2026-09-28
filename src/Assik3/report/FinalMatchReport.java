package Assik3.report;

import Assik3.implementor.BroadcastChannel;

public class FinalMatchReport extends MatchReport {
    private final String teams;
    private final String finalScore;
    private final String winner;
    private final String summary;
    private final String highlights;

    public FinalMatchReport(BroadcastChannel channel, String teams, String finalScore,
                            String winner, String summary, String highlights) {
        super(channel);
        this.teams = teams;
        this.finalScore = finalScore;
        this.winner = winner;
        this.summary = summary;
        this.highlights = highlights;
    }

    @Override
    protected String buildContent() {
        return "Матч завершён: " + teams
                + "\nИтоговый счёт: " + finalScore
                + "\nПобедитель: " + winner
                + "\nИтог: " + summary
                + "\nКлючевые события: " + highlights;
    }
}
