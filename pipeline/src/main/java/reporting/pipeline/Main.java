package reporting.pipeline;

import reporting.pipeline.publisher.PubSubPublisher;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        PubSubPublisher pubSubPublisher = new PubSubPublisher("projectId","topicId");
        pubSubPublisher.publish("2068/structured_data.json");
    }
}
