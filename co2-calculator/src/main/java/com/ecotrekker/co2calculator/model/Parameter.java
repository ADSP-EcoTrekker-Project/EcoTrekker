package com.ecotrekker.co2calculator.model;

public enum Parameter {
    BOOTSTRAP_SERVERS("--spring.kafka.bootstrap-servers"),
    GROUP_KEY("--spring.kafka.group.id"),
    AUTO_COMMIT_KEY("--spring.kafka.enable.auto.commit"),
    COMMIT_INTERVAL_MS_KEY("--spring.kafka.auto.commit.interval.ms"),
    TOPIC_KEY("--spring.kafka.topic.name"),
    REPLY_TOPIC_KEY("--spring.kafka.topic.reply");

    private String key;

    private Parameter(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getUsage() {
        switch (this) {
            case AUTO_COMMIT_KEY:
                return String.format("%s=true|false", key);
            case BOOTSTRAP_SERVERS:
                return String.format("%s=IP:PORT", key);
            case COMMIT_INTERVAL_MS_KEY:
                return String.format("%s=MILLIS", key);
            case GROUP_KEY:
                return String.format("%s=GROUP_NAME", key);
            case TOPIC_KEY:
            case REPLY_TOPIC_KEY:
                return String.format("%s=TOPIC_NAME", key);
            default:
                return "Error";
            
        }
    }
}
