package de.d3adspace.mercantor.core;

import de.d3adspace.mercantor.core.config.MercantorConfig;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorFactoryTest {
    @Test
    public void createMercantor() throws Exception {
        IMercantor mercantor = MercantorFactory.createMercantor();

        assertNotNull(mercantor);
    }

    @Test
    public void createMercantorWthConfig() throws Exception {
        MercantorConfig mercantorConfig = Mockito.mock(MercantorConfig.class);
        Mockito.verifyNoMoreInteractions(mercantorConfig);

        IMercantor mercantor = MercantorFactory.createMercantor(mercantorConfig);

        assertNotNull(mercantor);
    }
}