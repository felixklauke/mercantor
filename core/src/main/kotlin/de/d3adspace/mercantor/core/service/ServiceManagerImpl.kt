package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.core.heartbeat.HeartBeat
import java.util.*

class ServiceManagerImpl : ServiceManager {

    private val services = hashMapOf<UUID, Service>()

    override fun getService(vipAddress: String): Service {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getServices(vipAddress: String): Set<Service> {
        return services.filterValues {
            it.getVipAddress() == vipAddress
        }.values.toSet()
    }

    override fun handleHeartBeat(heartBeat: HeartBeat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getServices(): Set<Service> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun invalidate(serviceId: UUID) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun invalidate(service: Service) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun register(service: Service): Service {
        val randomUUID = UUID.randomUUID()
        service.setId(randomUUID)

        services.put(randomUUID, service)
        return service
    }
}