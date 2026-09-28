package Assik3.adapter;

import Assik3.exception.BroadcastException;
import Assik3.legacy.OldNewspaperPress;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewspaperBroadcasterAdapterTest {
    private static final class StubPress extends OldNewspaperPress {
        private int calls;
        private String article;
        private int sectionCode;
        private int result;
        private RuntimeException failure;

        @Override
        public int printArticle(String article, int sectionCode) {
            calls++;
            this.article = article;
            this.sectionCode = sectionCode;
            if (failure != null) {
                throw failure;
            }
            return result;
        }
    }

    @Test
    void mapsDestinationAndCallsLegacyOnce() {
        StubPress press = new StubPress();
        NewspaperBroadcasterAdapter adapter = new NewspaperBroadcasterAdapter(press);

        adapter.publish("news", "Матч идёт");

        assertEquals(1, press.calls);
        assertEquals("Матч идёт", press.article);
        assertEquals(1, press.sectionCode);

        adapter.publish("results", "Матч завершён");
        assertEquals(2, press.calls);
        assertEquals(2, press.sectionCode);
    }

    @Test
    void translatesLegacyErrorCodes() {
        StubPress press = new StubPress();
        NewspaperBroadcasterAdapter adapter = new NewspaperBroadcasterAdapter(press);

        press.result = -2;
        BroadcastException empty = assertThrows(BroadcastException.class,
                () -> adapter.publish("news", ""));
        assertTrue(empty.getMessage().contains("пуст"));

        press.result = -1;
        BroadcastException section = assertThrows(BroadcastException.class,
                () -> adapter.publish("results", "Матч"));
        assertTrue(section.getMessage().contains("раздел"));

        press.result = -99;
        assertThrows(BroadcastException.class, () -> adapter.publish("news", "Матч"));
        assertEquals(3, press.calls);
    }

    @Test
    void wrapsLegacyExceptionWithoutLosingCause() {
        StubPress press = new StubPress();
        RuntimeException failure = new IllegalStateException("old press failed");
        press.failure = failure;
        NewspaperBroadcasterAdapter adapter = new NewspaperBroadcasterAdapter(press);

        BroadcastException exception = assertThrows(BroadcastException.class,
                () -> adapter.publish("news", "Матч"));

        assertSame(failure, exception.getCause());
        assertEquals(1, press.calls);
    }

    @Test
    void rejectsUnknownDestinationBeforeCallingLegacy() {
        StubPress press = new StubPress();
        NewspaperBroadcasterAdapter adapter = new NewspaperBroadcasterAdapter(press);

        assertThrows(BroadcastException.class, () -> adapter.publish("unknown", "Матч"));

        assertEquals(0, press.calls);
    }
}
