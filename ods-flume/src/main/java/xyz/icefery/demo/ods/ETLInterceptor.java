package xyz.icefery.demo.ods;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ETLInterceptor implements Interceptor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void initialize() {}

    @Override
    public Event intercept(Event event) {
        try {
            String log = new String(event.getBody());
            objectMapper.readTree(log);
            return event;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        return events.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void close() {}

    public static class Builder implements Interceptor.Builder {
        @Override
        public Interceptor build() {
            return new ETLInterceptor();
        }

        @Override
        public void configure(Context context) {}
    }
}
