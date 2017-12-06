package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.core.service.ServiceManagerFactory

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
object MercantorFactory {

    fun createMercantor(): Mercantor {
        return MercantorImpl(ServiceManagerFactory.createServiceManager())
    }
}