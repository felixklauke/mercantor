package de.d3adspace.mercantor.shared.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class PrefixedThreadFactory implements ThreadFactory {

    private final AtomicInteger atomicInteger;
    private final String prefix;

    public PrefixedThreadFactory(String prefix) {
        this(new AtomicInteger(0), prefix);
    }

    public PrefixedThreadFactory(AtomicInteger atomicInteger, String prefix) {
        this.atomicInteger = atomicInteger;
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(prefix + atomicInteger.getAndIncrement());
        return thread;
    }
}
