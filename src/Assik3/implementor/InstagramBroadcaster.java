package Assik3.implementor;

import Assik3.exception.BroadcastException;

public class InstagramBroadcaster implements BroadcastChannel{
    @Override
    public String name() {
        return "instagram";
    }

    @Override
    public void publish(String destination, String content){
        if(destination == null|| destination.isBlank()){
            throw new BroadcastException("Не указан адресат Instagram");
        }
        if(content == null || content.isBlank()){
            throw new BroadcastException("Текст сообщения для Instagram пуст");
        }
        System.out.println("Имитация отправки в Instagram: " + destination);
        System.out.println("Сообщение:\n" + content);
    }
}
