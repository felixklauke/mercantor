package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.ServiceModel
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ServiceManagerImpl : ServiceManager {

    /**
     * Contains all services known to this instance.
     */
    private val services = ConcurrentHashMap<UUID, ServiceModel>()

    /**
     * The current state of the round robin selection process.
     */
    private var currentServiceIndices = ConcurrentHashMap<String, Int>()

    override fun getService(vipAddress: String): ServiceModel {
        val currentServices = getServices(vipAddress)

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

    override fun getServices(vipAddress: String): List<ServiceModel> {
        println("We have ${services.size} services.")


        return services.filterValues {
            it.getVipAddress() == vipAddress
        }.values.toList()
    }

    override fun handleHeartBeat(heartBeat: HeartBeat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getServices(): Set<ServiceModel> = services.values.toSet()

    override fun invalidate(serviceId: UUID) {
        services.remove(serviceId)
    }

    override fun invalidate(service: ServiceModel) {
        services.values.remove(service)
    }

    override fun register(service: ServiceModel): ServiceModel {
        val randomUUID = UUID.randomUUID()
        service.setId(randomUUID)

        services.put(randomUUID, service)
        return service
    }
}
