package Assik3.selection;

import Assik3.adapter.NewspaperBroadcasterAdapter;
import Assik3.implementor.InstagramBroadcaster;
import Assik3.implementor.TelegramBroadcaster;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RuntimeSelectionTest {
    @Test
    void selectsAllThreeChannelsFromRuntimeNames() {
        ChannelSelector selector = ChannelSelector.fromClasspath();

        assertInstanceOf(TelegramBroadcaster.class, selector.select("telegram"));
        assertInstanceOf(InstagramBroadcaster.class, selector.select("INSTAGRAM"));
        assertInstanceOf(NewspaperBroadcasterAdapter.class, selector.select("newspaper"));
        assertThrows(IllegalArgumentException.class, () -> selector.select("unknown"));
    }

}
