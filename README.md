ThreadLocal vs ThreadScope Demo (Spring Boot)
This project demonstrates the difference between ThreadLocal and Spring's SimpleThreadScope for managing per-thread beans in a multithreaded Spring Boot application.

Key Features
ThreadLocal: Manages a separate instance of AllDataChannels per thread with manual cleanup.

SimpleThreadScope: Spring’s built-in thread scope for non-web apps, using thread-bound storage.

GC Tracking: Uses finalize() logs to track bean destruction and lifecycle.

Manual Cleanup: Shows how to manually remove ThreadScoped beans at the end of thread execution.

UUID Tracing: Identifies thread-specific beans using UUIDs and instance counters.

Usage
Run the app to simulate parallel processing. Check logs for creation (✅), thread-local assignment (💡), and destruction (🔥) of beans. This helps understand the lifecycle differences and cleanup responsibility between the two approaches.
