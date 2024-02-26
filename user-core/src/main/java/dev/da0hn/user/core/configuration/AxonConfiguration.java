package dev.da0hn.user.core.configuration;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoSettingsFactory;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.config.SpringAxonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class AxonConfiguration {

  private final String mongoHost;

  private final Integer mongoPort;

  private final String mongoDatabase;

  public AxonConfiguration(
    @Value("${spring.data.mongodb.host}") final String mongoHost,
    @Value("${spring.data.mongodb.port}") final Integer mongoPort,
    @Value("${spring.data.mongodb.database}") final String mongoDatabase
  ) {
    this.mongoHost = mongoHost;
    this.mongoPort = mongoPort;
    this.mongoDatabase = mongoDatabase;
  }

  @Bean
  public MongoClient mongo() {
    final var mongoFactory = new MongoFactory();
    final var mongoSettingsFactory = new MongoSettingsFactory();
    mongoSettingsFactory.setMongoAddresses(Collections.singletonList(new ServerAddress(this.mongoHost, this.mongoPort)));
    mongoFactory.setMongoClientSettings(mongoSettingsFactory.createMongoClientSettings());
    return mongoFactory.createMongo();
  }

  @Bean
  public TokenStore tokenStore(final MongoClient mongoClient) {
    return MongoTokenStore.builder()
      .mongoTemplate(this.axonServerMongoTemplate(mongoClient))
      .serializer(JacksonSerializer.defaultSerializer())
      .build();
  }

  @Bean
  public EventStorageEngine storageEngine(final MongoClient mongoClient) {
    return MongoEventStorageEngine.builder()
      .mongoTemplate(this.axonServerMongoTemplate(mongoClient))
      .eventSerializer(JacksonSerializer.defaultSerializer())
      .snapshotSerializer(JacksonSerializer.defaultSerializer())
      .build();
  }

  @Bean
  public EmbeddedEventStore eventStore(
    final EventStorageEngine storageEngine,
    final SpringAxonConfiguration configuration
  ) {
    return EmbeddedEventStore.builder()
      .storageEngine(storageEngine)
      .messageMonitor(configuration.getObject().messageMonitor(Configuration.class, "eventStore"))
      .build();
  }

  private MongoTemplate axonServerMongoTemplate(final MongoClient client) {
    return DefaultMongoTemplate.builder()
      .mongoDatabase(client, "axon-event-store") // TODO: move to application.yml
      .build();
  }

}
