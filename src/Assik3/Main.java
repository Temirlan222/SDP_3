package Assik3;

import Assik3.implementor.BroadcastChannel;
import Assik3.report.FinalMatchReport;
import Assik3.report.LiveMatchReport;
import Assik3.selection.ChannelSelector;

import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            Scanner input = new Scanner(System.in);
            System.out.print("Канал (telegram, instagram, newspaper): ");
            if (!input.hasNextLine()) {
                return;
            }
            String channelName = input.nextLine().trim();
            System.out.print("Адресат (@match для Telegram, results для газеты): ");
            if (!input.hasNextLine()) {
                return;
            }
            args = new String[]{channelName, input.nextLine().trim()};
        }

        if (args.length != 2) {
            System.out.println("Использование: <telegram|instagram|newspaper> <destination>");
            return;
        }

        BroadcastChannel channel = ChannelSelector.fromClasspath().select(args[0]);

        new LiveMatchReport(channel, "Астана — Кайрат", "1:0", "32", "Гол хозяев")
                .publish(args[1]);
        new FinalMatchReport(channel, "Астана — Кайрат", "2:1", "Астана",
                "Победа хозяев", "Решающий гол на 67-й минуте")
                .publish(args[1]);
    }
}
