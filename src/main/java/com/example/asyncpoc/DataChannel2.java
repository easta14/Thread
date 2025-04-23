package com.example.asyncpoc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class DataChannel2 {
	private String temp;
	private String temp2;

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	@Override
	public String toString() {
		return "DataChannel2 [temp=" + temp + ", temp2=" + temp2 + "]";
	}
	
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize2() called for AllDataChannels | Object ref: " + System.identityHashCode(this));
        super.finalize();
    }
	
	

}
