package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.client.exception.NoSuchServiceException
import de.d3adspace.mercantor.client.util.RoundRobinList
import de.d3adspace.mercantor.commons.model.ServiceModel
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

class MercantorDiscoveryClientImpl(mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig) : MercantorDiscoveryClient {

    private val logger = LoggerFactory.getLogger(MercantorDiscoveryClientImpl::class.java)

    private val serviceLoader: ServiceLoader = ServiceLoader(mercantorDiscoveryClientConfig)
    private val currentServices: MutableMap<String, RoundRobinList<ServiceModel>> = ConcurrentHashMap()

    override fun discoverService(vipAddress: String): ServiceModel {
        logger.info("Discovering service for $vipAddress.")

        if (!currentServices.containsKey(vipAddress)) {
            logger.info("Didn't find a local copy of services for $vipAddress.")
            fetchServices(vipAddress)
        }

        val services = currentServices[vipAddress]

        if (services == null || services.isEmpty) {
            throw NoSuchServiceException("I don't own any services for $vipAddress")
        }

        return services.get()
    }

    private fun fetchServices(vipAddress: String) {
        logger.info("Beginning fetching from remote for $vipAddress.")

        val serviceContainer = ServiceContainer(vipAddress, serviceLoader)
        serviceContainer.services
                .subscribe({
                    logger.info("Got an update for $vipAddress.")

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