package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.client.exception.NoSuchServiceException
import de.d3adspace.mercantor.client.util.RoundRobinList
import de.d3adspace.mercantor.commons.model.ServiceModel
import java.util.concurrent.ConcurrentHashMap

class MercantorDiscoveryClientImpl(mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig) : MercantorDiscoveryClient {

    private val serviceLoader: ServiceLoader = ServiceLoader(mercantorDiscoveryClientConfig)
    private val currentServices: MutableMap<String, RoundRobinList<ServiceModel>> = ConcurrentHashMap()

    override fun discoverService(vipAddress: String): ServiceModel {
        if (!currentServices.containsKey(vipAddress)) {
            fetchServices(vipAddress)
        }

        val services = currentServices[vipAddress]

        if (services == null || services.isEmpty) {
            throw NoSuchServiceException("I don't own any services for $vipAddress")
        }

        return services.get()
    }

    private fun fetchServices(vipAddress: String) {
        val serviceContainer = ServiceContainer(vipAddress, serviceLoader)
        serviceContainer.services.subscribe({
            if (!currentServices.containsKey(vipAddress)) {
                val roundRobinList = RoundRobinList(it.toMutableList())
                currentServices.put(vipAddress, roundRobinList)
                return@subscribe
            }

            val roundRobinList = currentServices[vipAddress] ?: throw IllegalStateException()
            roundRobinList.setContent(it.toMutableList())
        })
    }
}