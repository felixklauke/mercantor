package de.d3adspace.mercantor.server.rest

import de.d3adspace.mercantor.core.Mercantor
import de.d3adspace.mercantor.server.config.MercantorServerConfig

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
object RestManagerFactory {

    fun createRestManager(mercantor: Mercantor, config: MercantorServerConfig): RestManager {
        return JerseyRestManager(mercantor, config)
    }
}
