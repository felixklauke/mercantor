package de.d3adspace.mercantor.core.thread;

import de.d3adspace.mercantor.core.MercantorConstants;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorThreadFactoryTest {
    @Test
    public void newThread() throws Exception {
        // Given
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Runnable runnable = Mockito.mock(Runnable.class);
        String expectedThreadName = MercantorConstants.MERCANTOR_WORKER_THREAD_PREFIX + atomicInteger.get();
        Thread expectedThread = Mockito.mock(Thread.class);
        MercantorThreadFactory mercantorThreadFactory = new MercantorThreadFactory(atomicInteger);

        // When
        PowerMockito.whenNew(Thread.class).withArguments(runnable).thenReturn(expectedThread);

        mercantorThreadFactory.newThread(runnable);

        // Then
        Mockito.verify(expectedThread).setName(expectedThreadName);
        assertEquals(1, atomicInteger.get());
    }
}