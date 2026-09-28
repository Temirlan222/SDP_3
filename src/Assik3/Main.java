package Assik3;

import Assik3.implementor.BroadcastChannel;
import Assik3.report.FinalMatchReport;
import Assik3.report.LiveMatchReport;
import Assik3.selection.ChannelSelector;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Использование: <telegram|instagram|newspaper> <destination>");
        }

        BroadcastChannel channel = ChannelSelector.fromClasspath().select(args[0]);

        new LiveMatchReport(channel, "Астана — Кайрат", "1:0", "32", "Гол хозяев")
                .publish(args[1]);
        new FinalMatchReport(channel, "Астана — Кайрат", "2:1", "Астана",
                "Победа хозяев", "Решающий гол на 67-й минуте")
                .publish(args[1]);
    }
}
