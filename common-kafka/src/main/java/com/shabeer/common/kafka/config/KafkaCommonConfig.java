package com.shabeer.common.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.shabeer.common.kafka.TopicNames;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaCommonConfig {

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name(TopicNames.ORDER_CREATED).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic orderValidatedTopic() {
        return TopicBuilder.name(TopicNames.ORDER_VALIDATED).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic inventoryReservedTopic() {
        return TopicBuilder.name(TopicNames.INVENTORY_RESERVED).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic routeCreatedTopic() {
        return TopicBuilder.name(TopicNames.ROUTE_CREATED).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic orderFulfilledTopic() {
        return TopicBuilder.name(TopicNames.ORDER_FULFILLED).partitions(3).replicas(1).build();
    }
}
