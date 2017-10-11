package de.d3adspace.mercantor.shared.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class PrefixedThreadFactory implements ThreadFactory {

    /**
     * Count for the threads.
     */
    private final AtomicInteger atomicInteger;

    /**
     * Prefix of the thread's names.
     */
    private final String prefix;

    /**
     * Create a new prefixed thread factory with a default count starting at 0.
     *
     * @param prefix The prefix of the thread's names.
     */
    public PrefixedThreadFactory(String prefix) {
        this(new AtomicInteger(0), prefix);
    }

    /**
     * Create a new prefixed thread factory by all its data.
     *
     * @param atomicInteger The thread count.
     * @param prefix        The prefix of the thread's names.
     */
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
