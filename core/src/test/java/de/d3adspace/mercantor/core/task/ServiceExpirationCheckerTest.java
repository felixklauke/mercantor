package de.d3adspace.mercantor.core.task;

import de.d3adspace.mercantor.core.IMercantor;
import de.d3adspace.mercantor.core.registry.Service;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceExpirationCheckerTest {
    @Test
    public void run() throws Exception {
        // Given
        IMercantor mercantor = Mockito.mock(IMercantor.class);
        ServiceExpirationChecker checker = new ServiceExpirationChecker(mercantor);

        // When

        checker.run();

        // Then
        Mockito.verify(mercantor, Mockito.times(1)).getServices();
        Mockito.verify(mercantor, Mockito.times(mercantor.getServices().size())).checkService(Mockito.any(Service.class));
    }
}