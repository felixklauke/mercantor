package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.client.exception.NoSuchServiceException
import de.d3adspace.mercantor.client.util.RoundRobinList
import de.d3adspace.mercantor.commons.model.ServiceModel

class MercantorDiscoveryClientImpl(private val mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig) : MercantorDiscoveryClient {

    private val currentServices = emptyMap<String, RoundRobinList<ServiceModel>>()

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

    }
}