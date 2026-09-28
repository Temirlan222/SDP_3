package Assik3.implementor;

import Assik3.exception.BroadcastException;

public class TelegramBroadcaster implements BroadcastChannel {
    @Override
    public String name() {
        return "telegram";
    }

    @Override
    public void publish(String destination, String content) {
        if (destination == null || destination.isBlank()) {
            throw new BroadcastException("Не указан адресат Telegram");
        }
        if (content == null || content.isBlank()) {
            throw new BroadcastException("Текст сообщения для Telegram пуст");
        }

        System.out.println("Имитация отправки в Telegram: " + destination);
        System.out.println("Сообщение:\n" + content);
    }
}
