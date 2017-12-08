package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ServiceManagerImpl : ServiceManager {

    /**
     * Contains all services known to this instance.
     */
    private val services = ConcurrentHashMap<UUID, Service>()

    /**
     * The current state of the round robin selection process.
     */
    private var currentServiceIndices = ConcurrentHashMap<String, Int>()

    /**
     * The current state of the round robin selection process.
     */
    private var currentServiceIndices = hashMapOf<String, Int>()

    override fun getService(vipAddress: String): Service {
        val currentServices = getServices()

        if (currentServices.size == 1) {
            return currentServices.first()
        }

        var index = currentServiceIndices.getOrPut(vipAddress, { 0 })

        index++

        if (index == currentServiceIndices.size) {
            index = 0
        }

        currentServiceIndices.put(vipAddress, index)
        return currentServices.elementAt(index)
    }

    override fun getServices(vipAddress: String): List<Service> {
        return services.filterValues {
            it.getVipAddress() == vipAddress
        }.values.toList()
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