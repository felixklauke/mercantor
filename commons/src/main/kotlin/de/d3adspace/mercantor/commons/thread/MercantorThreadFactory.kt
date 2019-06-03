package de.d3adspace.mercantor.commons.thread

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class MercantorThreadFactory(private val namePrefix: String) : ThreadFactory {

    private val threadIndex = AtomicInteger()

    override fun newThread(runnable: Runnable): Thread {
        val thread = Thread(runnable)
        thread.name = "$namePrefix #${threadIndex.getAndIncrement()}"
        return thread
    }
}
