package reporting.pipeline.publisher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import reporting.proto.Match;
import reporting.proto.Match.Frames;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import com.google.protobuf.util.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PubSubPublisher {

    private final String projectId;
    private final String topicId;

    public PubSubPublisher(String projectId, String topicId) {
        this.projectId = projectId;
        this.topicId = topicId;
    }

    public void publish(String jsonFilePath) throws URISyntaxException {

        URL resourceURL = PubSubPublisher.class.getClassLoader().getResource(jsonFilePath);
        if (resourceURL != null){
            File jsonFile = new File(resourceURL.toURI());
            try {
                byte[] jsonBytes = Files.readAllBytes(jsonFile.toPath());
                String jsonArray = new String(jsonBytes, "UTF-8");
                String json = "{\"frames\":" + jsonArray + "}";

                Frames.Builder builder = Frames.newBuilder();

                JsonFormat.parser().merge(json, builder);
                Frames frames = builder.build();
                frames.getFramesList().forEach(frame -> {
                    System.out.println(frame.getPossession().getGroup());
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

}

