package com.example.logger.model;

import org.apache.logging.log4j.core.LogEvent;

public class LogPayload {

    private LogEvent logEvent;
    private String traceId;

    public LogPayload(LogEvent logEvent, String traceId) {
        this.logEvent = logEvent;
        this.traceId = traceId;
    }
}
