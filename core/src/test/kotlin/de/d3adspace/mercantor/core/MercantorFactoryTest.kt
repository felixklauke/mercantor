package de.d3adspace.mercantor.core

import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class MercantorFactoryTest {

    @Test
    fun createMercantor() {
        val mercantor = MercantorFactory.createMercantor()

        assertNotNull(mercantor)
    }
}
