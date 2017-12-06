package de.d3adspace.mercantor.server.rest

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
object RestManagerFactory {

    fun createRestManager(): RestManager {
        return JerseyRestManager()
    }
}