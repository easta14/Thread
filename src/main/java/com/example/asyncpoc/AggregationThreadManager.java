package com.example.asyncpoc;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AggregationThreadManager {

    @Autowired
    private AggregationThread aggregationThread;

    @Async
    public void handleAggregation(String id, long startTime, String channels) {
        aggregationThread.processAggregation(id, startTime, channels);
    }
}
