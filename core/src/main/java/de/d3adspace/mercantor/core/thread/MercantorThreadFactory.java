package de.d3adspace.mercantor.core.thread;

import de.d3adspace.mercantor.core.MercantorConstants;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread factory used to name the threads properly.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorThreadFactory implements ThreadFactory {

    private final AtomicInteger threadIdCounter;

    public MercantorThreadFactory() {
        this(new AtomicInteger());
    }

    public MercantorThreadFactory(AtomicInteger threadIdCounter) {
        this.threadIdCounter = threadIdCounter;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(MercantorConstants.MERCANTOR_WORKER_THREAD_PREFIX + threadIdCounter.getAndIncrement());
        return thread;
    }
}
