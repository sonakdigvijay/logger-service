package com.example.logger.appender;

import com.example.logger.model.LogPayload;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

@Plugin(name = "LoggerKafkaAppender", category = "Core", elementType = "ppender", printObject = true)
public class LoggerKafkaAppender extends AbstractAppender {

    private Gson gson = new Gson();

    public LoggerKafkaAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @PluginFactory
    public static LoggerKafkaAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginElement("properties") Property[] properties
    ) {
        if (name == null) {
            LOGGER.error("No name provided for LoggerKafkaAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new LoggerKafkaAppender(name, filter, layout, true, properties);
    }

    @Override
    public void append(LogEvent logEvent) {
        KafkaProducer<String, String> producer = getKafaLoggerProducer();
        producer.send(new ProducerRecord<String, String>(logEvent.getLevel().toString(), gson.toJson(new LogPayload(logEvent, UUID.randomUUID().toString()))));
    }

    private KafkaProducer<String, String> getKafaLoggerProducer() {
        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", "127.0.0.1:9092");
        producerProperties.put("acks", "all");
        producerProperties.put("retries", 0);
        producerProperties.put("batch.size", 16384);
        producerProperties.put("linger.ms", 1);
        producerProperties.put("buffer.memory", 33554432);
        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(producerProperties);
    }

}
