package com.example.asyncpoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
@Scope("thread")
public class AllDataChannels{
    private static int counter = 0;
    private final int instanceId;
    private String measurementId;
    private String startOfTimeFrame;
    @Autowired
    private DataChannel dataChannel;

    public DataChannel getDataChannel() {
		return dataChannel;
	}
	public void setDataChannel(DataChannel dataChannel) {
		this.dataChannel = dataChannel;
	}
	public String getMeasurementId() { return measurementId; }
    public void setMeasurementId(String measurementId) { this.measurementId = measurementId; }

    public String getStartOfTimeFrame() { return startOfTimeFrame; }
    public void setStartOfTimeFrame(String startOfTimeFrame) { this.startOfTimeFrame = startOfTimeFrame; }
    
    public AllDataChannels() {
        instanceId = ++counter;
        System.out.println("✅ AllDataChannels created: instanceId=" + instanceId + " | Object ref: " + System.identityHashCode(this));
    }
    
    @PreDestroy
    // Manually call cleanup (for demonstration)
    public void cleanup() {
        System.out.println("Destroying thread-scoped bean: " + this + " | Object ref: " + System.identityHashCode(this));
    }
    
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize() called for AllDataChannels [instanceId=" + instanceId + "] | Object ref: " + System.identityHashCode(this));
        super.finalize();
    }
    
    @Override
    public String toString() {
        return "AllDataChannels{instanceId=" + instanceId + "}";
    }
}
