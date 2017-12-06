package de.d3adspace.mercantor.server.rest

import de.d3adspace.mercantor.core.Mercantor

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
object RestManagerFactory {

    fun createRestManager(mercantor: Mercantor): RestManager {
        return JerseyRestManager(mercantor)
    }
}