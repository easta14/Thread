package com.example.asyncpoc;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AggregationRawThread {
	
	private final BlockingQueue<String> rawMessageQueue = new LinkedBlockingQueue<>();

	@Async
    public void enqueueRawMessage(String message) {
        try {
            rawMessageQueue.put(message);
            System.out.println(Thread.currentThread().getName() + " -> Enqueued raw message: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String takeRawMessage() throws InterruptedException {
        return rawMessageQueue.take();
    }
}
