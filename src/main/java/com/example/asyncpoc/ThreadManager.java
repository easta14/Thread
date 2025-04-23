package com.example.asyncpoc;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadManager {

	private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
	
	 @Async
	    public void enqueueMessage(String message) {
	        try {
	            messageQueue.put(message);
	            System.out.println(Thread.currentThread().getName() + " -> Enqueued message: " + message);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }

	    public String takeMessage() throws InterruptedException {
	        return messageQueue.take();
	    }
}
