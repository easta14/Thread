package com.example.asyncpoc;

import org.springframework.context.support.SimpleThreadScope;

public class CustomeThreadScope extends SimpleThreadScope {

	    private final ThreadLocal<AllDataChannels> threadLocal = ThreadLocal.withInitial(AllDataChannels::new);

	    @Override
	    public Object remove(String name) {
	        AllDataChannels bean = threadLocal.get();
	        if (bean != null) {
	            // Manually clean up resources or log destruction
	            System.out.println("Manually destroying bean: " + bean);
	        }
	        return super.remove(name);
	    }

	    public void cleanup() {
	        // Manually clear the thread-scoped beans
	        threadLocal.remove();
	    }
	}