package de.d3adspace.mercantor.server.application

import de.d3adspace.mercantor.commons.codec.GsonJaxRSProvider
import de.d3adspace.mercantor.core.Mercantor
import de.d3adspace.mercantor.server.resource.MercantorServerResource
import javax.ws.rs.core.Application

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class MercantorServerApplication(val mercantor: Mercantor) : Application() {

    override fun getSingletons(): MutableSet<Any> {
        return mutableSetOf(MercantorServerResource(mercantor), GsonJaxRSProvider())
    }
}