package com.example.asyncpoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class AllDataChannels2{
    private static int counter = 0;
    private final int instanceId;
    private String measurementId;
    private String startOfTimeFrame;
    @Autowired
    private DataChannel2 dataChannel;

    public DataChannel2 getDataChannel() {
		return dataChannel;
	}
	public void setDataChannel(DataChannel2 dataChannel) {
		this.dataChannel = dataChannel;
	}
	public String getMeasurementId() { return measurementId; }
    public void setMeasurementId(String measurementId) { this.measurementId = measurementId; }

    public String getStartOfTimeFrame() { return startOfTimeFrame; }
    public void setStartOfTimeFrame(String startOfTimeFrame) { this.startOfTimeFrame = startOfTimeFrame; }
    
    public AllDataChannels2() {
        instanceId = ++counter;
        System.out.println("🔥 AllDataChannels created: instanceId=" + instanceId + " | Object ref: " + System.identityHashCode(this));
    }
    
    
    @PreDestroy
    // Manually call cleanup (for demonstration)
    public void cleanup() {
        System.out.println("Destroying thread-scoped bean: " + this + " | Object ref: " + System.identityHashCode(this));
    }
    
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize2() called for AllDataChannels [instanceId=" + instanceId + "] | Object ref: " + System.identityHashCode(this));
        super.finalize();
    }
    
    @Override
    public String toString() {
        return "AllDataChannels2{instanceId=" + instanceId + "}";
    }
}
