package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.core.heartbeat.HeartBeat
import de.d3adspace.mercantor.core.service.Service
import de.d3adspace.mercantor.core.service.ServiceManager

class MercantorImpl(private val serviceManager: ServiceManager) : Mercantor {

    override fun handleServiceHeartBeat(heartBeat: HeartBeat) {
        serviceManager.handleHeartBeat(heartBeat)
    }

    override fun getServices(): Set<Service> {
        return serviceManager.getServices()
    }

    override fun invalidateService(service: Service) {
        serviceManager.invalidate(service)
    }

    override fun registerService(service: Service) {
        serviceManager.register(service)
    }
}