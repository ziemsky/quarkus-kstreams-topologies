package org.acme.qkt;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;

@ApplicationScoped
public class TopologyProducerA {

    @Produces
    public Topology topologyA() {
        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        streamsBuilder
            .stream("topic-a", Consumed.with(Serdes.String(), Serdes.String()))

            .foreach((key, value) -> Log.infof("Consuming topic-a %s : %s", key, value));

        return streamsBuilder.build();
    }
}
