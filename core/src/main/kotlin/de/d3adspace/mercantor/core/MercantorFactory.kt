package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.core.service.ServiceRepositoryImpl

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
object MercantorFactory {

    fun createMercantor(): Mercantor {
        val serviceRepository = ServiceRepositoryImpl()
        return MercantorImpl(serviceRepository)
    }
}
