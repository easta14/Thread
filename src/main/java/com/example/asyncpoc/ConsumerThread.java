package com.example.asyncpoc;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ConsumerThread {
	
	@Autowired
	private DataChannel dataChannel;
	

    private final ThreadLocal<DataChannel2> threadData = ThreadLocal.withInitial(() -> {
    	DataChannel2 data = new DataChannel2();   // Will print creation log
        System.out.println("💡 ThreadLocal created AllDataChannels: " + data);
        return data;
    });

    @Autowired
    private ThreadManager threadManager;

    @Autowired
    private AggregationRawThread aggregationRawThread;

    @Autowired
    private AggregationThreadManager aggregationThreadManager;
    @Autowired
    private ConfigurableApplicationContext context;

    @Async
    public void startConsuming() {
        int i = 0;
        while (i < 5) {
            String data = "Data-" + i;
            threadManager.enqueueMessage(data + "-T1");
            aggregationRawThread.enqueueRawMessage(data + "-T2");
            aggregationThreadManager.handleAggregation("Meas-" + i, System.currentTimeMillis(), "ChannelGroup-" + i);
            i++;
            dataChannel.setTemp2("static Data");
            DataChannel2 channelData = threadData.get();
            channelData.setTemp("Static Data2");
            System.out.println("static data: " +dataChannel);
            System.out.println("static data2: " +channelData);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        SpringApplication.exit(context);
    }
}
