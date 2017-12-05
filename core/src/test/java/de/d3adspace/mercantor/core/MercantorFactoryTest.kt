package de.d3adspace.mercantor.core

import org.junit.Assert.assertNotNull

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class MercantorFactoryTest {

    @org.junit.Test
    fun createMercantor() {
        val mercantor = MercantorFactory.createMercantor()

        assertNotNull(mercantor)
    }
}