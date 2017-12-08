package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.Service
import de.d3adspace.mercantor.core.service.ServiceManager
import java.util.*

class MercantorImpl(private val serviceManager: ServiceManager) : Mercantor {

    override fun invalidateService(serviceId: UUID) {
        serviceManager.invalidate(serviceId)
    }

    override fun handleServiceHeartBeat(heartBeat: HeartBeat) {
        serviceManager.handleHeartBeat(heartBeat)
    }

    override fun getService(vipAddress: String): Service {
        return serviceManager.getService(vipAddress)
    }

    override fun invalidateService(service: Service) {
        serviceManager.invalidate(service)
    }

    override fun registerService(service: Service): Service {
        return serviceManager.register(service)
    }
}