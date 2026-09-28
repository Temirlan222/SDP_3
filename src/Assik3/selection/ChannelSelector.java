package Assik3.selection;

import Assik3.implementor.BroadcastChannel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

public final class ChannelSelector {
    private final Map<String, BroadcastChannel> channels;

    public ChannelSelector(Iterable<BroadcastChannel> availableChannels) {
        Objects.requireNonNull(availableChannels, "availableChannels");
        Map<String, BroadcastChannel> found = new HashMap<>();
        for (BroadcastChannel channel : availableChannels) {
            String name = normalize(channel.name());
            if (found.putIfAbsent(name, channel) != null) {
                throw new IllegalArgumentException("Канал зарегистрирован дважды: " + name);
            }
        }
        this.channels = Map.copyOf(found);
    }

    public static ChannelSelector fromClasspath() {
        return new ChannelSelector(ServiceLoader.load(BroadcastChannel.class));
    }

    public BroadcastChannel select(String channelName) {
        BroadcastChannel channel = channels.get(normalize(channelName));
        if (channel == null) {
            throw new IllegalArgumentException("Неизвестный канал: " + channelName);
        }
        return channel;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Название канала не задано");
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }
}
