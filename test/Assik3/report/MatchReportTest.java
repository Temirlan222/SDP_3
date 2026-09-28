package Assik3.report;

import Assik3.implementor.BroadcastChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchReportTest {
    private static final class RecordingChannel implements BroadcastChannel {
        private int calls;
        private String destination;
        private String content;

        @Override
        public void publish(String destination, String content) {
            calls++;
            this.destination = destination;
            this.content = content;
        }
    }

    @Test
    void liveReportDelegatesCurrentMatchContent() {
        RecordingChannel channel = new RecordingChannel();
        MatchReport report = new LiveMatchReport(channel, "Астана — Кайрат", "2:1",
                "67", "Гол хозяев");

        report.publish("@match");

        assertEquals(1, channel.calls);
        assertEquals("@match", channel.destination);
        assertTrue(channel.content.contains("Идёт матч"));
        assertTrue(channel.content.contains("2:1"));
        assertTrue(channel.content.contains("67"));
        assertTrue(channel.content.contains("Гол хозяев"));
    }

    @Test
    void finalReportDelegatesFinalMatchContent() {
        RecordingChannel channel = new RecordingChannel();
        MatchReport report = new FinalMatchReport(channel, "Астана — Кайрат", "2:1",
                "Астана", "Победа хозяев", "Решающий гол на 67-й минуте");

        report.publish("results");

        assertEquals(1, channel.calls);
        assertEquals("results", channel.destination);
        assertTrue(channel.content.contains("Матч завершён"));
        assertTrue(channel.content.contains("Астана"));
        assertTrue(channel.content.contains("Победа хозяев"));
        assertTrue(channel.content.contains("Решающий гол"));
    }
}
