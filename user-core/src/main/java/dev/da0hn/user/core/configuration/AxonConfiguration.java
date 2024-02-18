package dev.da0hn.user.core.configuration;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoSettingsFactory;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
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
  public MongoTemplate axonMongoTemplate() {
    return DefaultMongoTemplate.builder()
      .mongoDatabase(this.mongo(), this.mongoDatabase)
      .build();
  }

  @Bean
  public TokenStore tokenStore(
    final Serializer eventSerializer,
    final MongoTemplate mongoTemplate
  ) {
    return MongoTokenStore.builder()
      .mongoTemplate(mongoTemplate)
      .serializer(eventSerializer)
      .build();
  }

}
