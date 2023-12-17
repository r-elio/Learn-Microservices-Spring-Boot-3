package microservices.book.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class LogsConsumer {
    private static final Logger log = LoggerFactory.getLogger(LogsConsumer.class);

    @RabbitListener(queues = "logs.queue")
    public void log(final String msg, @Header("level") String level, @Header("amqp_appId") String appId) {
        Marker marker = MarkerFactory.getMarker(appId);
        switch (level) {
            case "INFO" -> log.info(marker, msg);
            case "ERROR" -> log.error(marker, msg);
            case "WARN" -> log.warn(marker, msg);
        }
    }
}
