package com.kroger.merchandising.magicdatareader.configuration;

import com.kroger.streaming.configuration.DespKafkaProperties;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

/**
 * This is Config class for to load the kafka configuration and connect to Kafka through DESP.
 */
@ConditionalOnProperty(
        prefix = "spring.kafka",
        value = "producer.enabled",
        havingValue = "true",
        matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(DespKafkaProperties.class)
public class KafkaConfig {

    private final DespKafkaProperties despKafkaProperties;

    @Value("${spring.kafka.mes.request-timeout-ms-config}")
    private Integer maxRequestTimeoutMs;
    @Value("${spring.kafka.mes.buffer-memory-config}")
    private Long bufferMemory;
    @Value("${spring.kafka.mes.linger-ms-config}")
    private Integer lingerMs;
    @Value("${spring.kafka.mes.max-request-size-config}")
    private Integer maxRequestSize;
    @Value("${spring.kafka.mes.batch-size-config}")
    private Integer batchSize;
    @Value("${spring.kafka.mes.retries-config}")
    private Integer retries;
    @Value("${spring.kafka.mes.max-age}")
    Integer maxAge;
    @Value("${spring.kafka.mes.enable-idempotence}")
    Boolean enableIdempotence;

    public KafkaConfig(DespKafkaProperties despKafkaProperties) {
        this.despKafkaProperties = despKafkaProperties;
    }


    @Bean
    public KafkaTemplate<String, SpecificRecord> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
    }


    private Map<String, Object> producerConfig() {
        Map<String, Object> properties = despKafkaProperties.buildProducerProperties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.put("auto.register.schemas", Boolean.FALSE);
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, maxRequestTimeoutMs);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, maxRequestSize);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        properties.put(ProducerConfig.RETRIES_CONFIG, retries);
        properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, maxAge);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        return properties;
    }

}

