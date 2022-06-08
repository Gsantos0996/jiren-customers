//package com.jiren.shared.kafka.config;
//
//import org.apache.kafka.clients.CommonClientConfigs;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.config.SaslConfigs;
//import org.apache.kafka.common.security.scram.ScramLoginModule;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.jiren.shared.kafka.props.KafkaProperties;
//
//@EnableKafka
//@Configuration
//public class KafkaConfig {
//
//    public ConsumerFactory<String, String> consumerFactory(KafkaProperties kafkaProperties) {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "jiren-customers-group-id");
//        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
//        config.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSaslMechanism());
//        config.put(SaslConfigs.SASL_JAAS_CONFIG, String.format("%s required username=%s password=%s;",
//                ScramLoginModule.class.getName(), System.getenv("KAFKA_USERNAME"), System.getenv("KAFKA_PASSWORD")));
//
//        return new DefaultKafkaConsumerFactory<>(config);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory(kafkaProperties));
//
//        return factory;
//    }
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory(KafkaProperties kafkaProperties) {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
//        config.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSaslMechanism());
//        config.put(SaslConfigs.SASL_JAAS_CONFIG, String.format("%s required username='%s' password='%s';",
//                ScramLoginModule.class.getName(), System.getenv("KAFKA_USERNAME"), System.getenv("KAFKA_PASSWORD")));
//
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate(KafkaProperties kafkaProperties) {
//        return new KafkaTemplate<>(producerFactory(kafkaProperties));
//    }
//
//}
