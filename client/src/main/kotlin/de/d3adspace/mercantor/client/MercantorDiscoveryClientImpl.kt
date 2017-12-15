package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.client.exception.NoSuchServiceException
import de.d3adspace.mercantor.client.util.RoundRobinList
import de.d3adspace.mercantor.commons.model.ServiceModel
import java.util.concurrent.ConcurrentHashMap

class MercantorDiscoveryClientImpl(private val mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig) : MercantorDiscoveryClient {

    private val serviceLoader: ServiceLoader = ServiceLoader()
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
        val services: List<ServiceModel> = serviceLoader.loadServices(vipAddress)

        val roundRobinList = RoundRobinList(services.toMutableList())
        currentServices.put(vipAddress, roundRobinList)
    }
}