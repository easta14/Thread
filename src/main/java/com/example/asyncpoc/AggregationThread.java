package com.example.asyncpoc;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

@Service
public class AggregationThread {

    private final ThreadManager threadManager;
    private final AggregationRawThread aggregationRawThread;

    private final AllDataChannels allDataChannels;
    private final ThreadLocal<AllDataChannels2> threadLocal = ThreadLocal.withInitial(() -> {
        AllDataChannels2 data = new AllDataChannels2();   // Will print creation log
        System.out.println("💡 ThreadLocal created AllDataChannels: " + data);
        return data;
    });
    private final ThreadLocal<DataChannel2> threadData = ThreadLocal.withInitial(() -> {
    	DataChannel2 data = new DataChannel2();   // Will print creation log
        System.out.println("💡 ThreadLocal created AllDataChannels: " + data);
        return data;
    });
    private final DataChannel datachannel;

    @Autowired
    public AggregationThread(ThreadManager threadManager,
                               AggregationRawThread aggregationRawThread,
                               AllDataChannels allDataChannels, DataChannel datachannel) {
        this.threadManager = threadManager;
        this.aggregationRawThread = aggregationRawThread;
        this.allDataChannels = allDataChannels;
        this.datachannel = datachannel;
        // Initialize ThreadLocal
//        this.threadLocal = ThreadLocal.withInitial(() -> {
//            AllDataChannels data = new AllDataChannels("a");   // Will print creation log
//            System.out.println("💡 ThreadLocal created AllDataChannels: " + data);
//            return data;
//        });
//        
//        this.threadData = ThreadLocal.withInitial(() -> {
//        	DataChannel data = new DataChannel();   // Will print creation log
//            System.out.println("💡 ThreadLocal created AllDataChannels: " + data);
//            return data;
//        });
    }

    @Async
    public void processAggregation(String id, long startTime, String channels) {
        AllDataChannels2 localData = threadLocal.get();
        localData.setMeasurementId(UUID.randomUUID().toString());

        // Thread-Scoped Bean approach
        allDataChannels.setMeasurementId(UUID.randomUUID().toString());

        System.out.println("ThreadLocal ID: " + localData.getMeasurementId() +
                           " | ThreadScopedBean ID: " + allDataChannels.getMeasurementId() +
                           " | Thread Name: " + Thread.currentThread().getName());
        
        try {
            String messageFromManager = threadManager.takeMessage();
            String rawMessage = aggregationRawThread.takeRawMessage();
            
            datachannel.setTemp(messageFromManager);
            allDataChannels.setDataChannel(datachannel);//ThreadScope
            
            DataChannel2 channelData = threadData.get();
            channelData.setTemp(messageFromManager);
            localData.setDataChannel(channelData);//ThreadLocal


            System.out.println("Thread Scope: " + allDataChannels.getDataChannel());
            System.out.println("Thread Local: " + localData.getDataChannel());
//            System.out.println("Processing from ThreadManager: " + messageFromManager +
//                               " | Thread: " + Thread.currentThread().getName());
//            System.out.println("Processing from AggregationRawThread: " + rawMessage +
//                               " | Thread: " + Thread.currentThread().getName());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while taking messages.");
        }
        finally {
        	threadLocal.remove();
        	threadData.remove();
        }

        System.out.println("ThreadLocal ID: " + localData.getMeasurementId() +
                " | ThreadScopedBean ID: " + allDataChannels.getMeasurementId() +
                " | Thread Name: " + Thread.currentThread().getName());
        
        System.out.println("Finished processing Aggregation for ID: " + id +
                           " | Thread Name: " + Thread.currentThread().getName());
    }
    
    // Cleanup method to be called on bean destruction or before thread scope ends
    @PreDestroy
    public void cleanup() throws InterruptedException {
        // Print threadLocal before cleanup
        AllDataChannels2 localDataBeforeCleanup = threadLocal.get();
        System.out.println("Before cleanup: threadLocal value = " + localDataBeforeCleanup);

        System.out.println("Cleaning up resources in AggregationThread...");

        // Clean up ThreadLocal
        threadLocal.remove();

        System.out.println("ThreadLocal reference removed.");
        
        // Suggest GC
        System.gc();
        
        Thread.sleep(2000);

        System.out.println("After cleanup: threadLocal value = " + threadLocal.get());

        System.out.println("ThreadLocal variables and other resources cleaned up.");
    }
}
