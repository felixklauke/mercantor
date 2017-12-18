package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.client.exception.NoSuchServiceException
import de.d3adspace.mercantor.client.result.DiscoveryResult
import de.d3adspace.mercantor.client.util.RoundRobinList
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import io.reactivex.Observable
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * The default implementation of the discovery client.
 */
class MercantorDiscoveryClientImpl(private val mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig) : MercantorDiscoveryClient {

    /**
     * The logger of all actions.
     */
    private val logger = LoggerFactory.getLogger(MercantorDiscoveryClientImpl::class.java)

    /**
     * The service loader.
     */
    private val serviceLoader: ServiceLoader = ServiceLoader(mercantorDiscoveryClientConfig)

    /**
     * All currently loaded services.
     */
    private val currentServices: MutableMap<String, RoundRobinList<ServiceModel>> = ConcurrentHashMap()

    /**
     * All currently active agents.
     */
    private val currentAgents: MutableMap<UUID, ServiceAgent> = ConcurrentHashMap()

    /**
     * Discover an instance of the given service.
     */
    override fun discoverService(vipAddress: String): DiscoveryResult {
        logger.info("Discovering service for $vipAddress.")

        if (!currentServices.containsKey(vipAddress)) {
            logger.info("Didn't find a local copy of services for $vipAddress.")
            fetchServices(vipAddress)
        }

        val services = currentServices[vipAddress]

        if (services == null || services.isEmpty) {
            throw NoSuchServiceException("I don't own any services for $vipAddress")
        }

        val model = services.get()
        return DiscoveryResult(model.instanceId, model.hostName, model.ipAddress, model.port)
    }

    /**
     * Fetch the instances for the given service.
     */
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

    /**
     * Register a new service instance for the given data.
     */
    override fun registerService(vipAddress: String, name: String, host: String, ipAddress: String, port: Int, metaData: Map<String, Any>): Observable<UUID> {
        logger.info("Registering service for $vipAddress named $name that can be found at $host or $ipAddress:$port.")

        val model = ServiceModel(UUID.randomUUID(), vipAddress, ipAddress, host, port, name, ServiceStatus.STARTING_UP, metaData)
        val serviceAgent = ServiceAgent(mercantorDiscoveryClientConfig, model)

        val serviceObservable = serviceAgent.registerService()

        serviceObservable.subscribe {
            model.status = ServiceStatus.UP

            serviceAgent.startHeartbeats()

            logger.info("Registered service for $vipAddress. It got the instance id ${model.instanceId}")

            currentAgents.put(model.instanceId, serviceAgent)
        }

        return serviceObservable
    }

    /**
     * Unregister the instance behind the given instance id.
     */
    override fun unregisterService(instanceId: UUID) {
        logger.info("Unregistering service with instance id $instanceId.")

        currentAgents[instanceId]?.destroy()
        currentAgents.remove(instanceId)
    }
}