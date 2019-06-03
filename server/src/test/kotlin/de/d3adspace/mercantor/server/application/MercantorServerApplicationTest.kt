package de.d3adspace.mercantor.server.application

import de.d3adspace.mercantor.core.MercantorFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class MercantorServerApplicationTest {

    private val app = MercantorServerApplication(MercantorFactory.createMercantor())

    @Test
    fun getSingletons() {
        assertEquals(2, app.singletons.size)
    }
}
