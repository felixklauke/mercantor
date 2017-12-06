package de.d3adspace.mercantor.server.application

import de.d3adspace.mercantor.server.resource.MercantorServerResource
import javax.ws.rs.core.Application

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class MercantorServerApplication : Application() {

    override fun getSingletons(): MutableSet<Any> {
        return mutableSetOf(MercantorServerResource())
    }
}