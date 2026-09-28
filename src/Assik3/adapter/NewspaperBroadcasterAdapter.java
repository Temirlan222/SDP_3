package Assik3.adapter;

import Assik3.exception.BroadcastException;
import Assik3.implementor.BroadcastChannel;
import Assik3.legacy.OldNewspaperPress;

import java.util.Objects;

public class NewspaperBroadcasterAdapter implements BroadcastChannel {
    private final OldNewspaperPress legacyNewspaper;

    public NewspaperBroadcasterAdapter() {
        this(new OldNewspaperPress());
    }

    @Override
    public String name() {
        return "newspaper";
    }

    public NewspaperBroadcasterAdapter(OldNewspaperPress legacyNewspaper) {
        this.legacyNewspaper = Objects.requireNonNull(legacyNewspaper, "legacyNewspaper");
    }

    @Override
    public void publish(String destination, String content) {
        int sectionCode;
        if ("news".equals(destination)) {
            sectionCode = 1;
        } else if ("results".equals(destination)) {
            sectionCode = 2;
        } else {
            throw new BroadcastException("Неизвестный газетный раздел: " + destination);
        }

        int result;
        try {
            result = legacyNewspaper.printArticle(content, sectionCode);
        } catch (RuntimeException cause) {
            throw new BroadcastException("Не удалось опубликовать статью в газете", cause);
        }

        if (result == 0) {
            return;
        }
        if (result == -1) {
            throw new BroadcastException("Газетный раздел недоступен");
        }
        if (result == -2) {
            throw new BroadcastException("Текст газетной статьи пуст");
        }
        throw new BroadcastException("Не удалось опубликовать статью в газете");
    }
}
