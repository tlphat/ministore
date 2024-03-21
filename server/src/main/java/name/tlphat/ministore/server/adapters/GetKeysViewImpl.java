package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.usecases.dto.Key;
import name.tlphat.ministore.server.usecases.ports.GetKeysView;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GetKeysViewImpl implements GetKeysView {

    private static final String EMPTY_MESSAGE = "EMPTY";
    private static final String LINE_FORMAT = "%s)\t\t%s\t\t%s%n";

    @Override
    public String prepareSuccessfulView(List<Key> keys) {
        if (keys.isEmpty()) {
            return EMPTY_MESSAGE;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        final AtomicInteger counter = new AtomicInteger(1);
        keys.forEach(key -> stringBuilder.append(
            String.format(LINE_FORMAT, counter.getAndIncrement(), key.name(), key.type())));
        return stringBuilder.toString();
    }
}
