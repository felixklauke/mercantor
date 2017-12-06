package de.d3adspace.mercantor.core.service

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface ServiceManager {

    fun getServices(): Set<Service>

    fun invalidate(service: Service)

    fun register(service: Service)
}