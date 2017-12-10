package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.ServiceClusterModel
import de.d3adspace.mercantor.commons.model.service.ServiceModel
import de.d3adspace.mercantor.core.service.ServiceManager
import org.slf4j.LoggerFactory
import java.util.*

class MercantorImpl(private val serviceManager: ServiceManager) : Mercantor {

    /**
     * The logger to log all general actions on services.
     */
    private val logger = LoggerFactory.getLogger(MercantorImpl::class.java)

    override fun getServices(vipAddress: String, limit: Int): ServiceClusterModel {
        logger.info("Querying vip address group $vipAddress.")

        return when (limit) {
            -1 -> ServiceClusterModel(serviceManager.getServices(vipAddress))
            else -> ServiceClusterModel(serviceManager.getServices(vipAddress).shuffled().take(limit))
        }
    }

    override fun invalidateService(serviceId: UUID) {
        logger.info("Invalidating service with service id $serviceId.")

        serviceManager.invalidate(serviceId)
    }

    override fun handleServiceHeartBeat(heartBeat: HeartBeat) {
        logger.info("Received heartbeat for service ${heartBeat.getServiceId()}.")

        serviceManager.handleHeartBeat(heartBeat)
    }

    override fun getService(vipAddress: String): ServiceModel {
        logger.info("Querying single service for $vipAddress")

        return serviceManager.getService(vipAddress)
    }

    override fun invalidateService(service: ServiceModel) {
        logger.info("Invalidating service ${service.getId()}")

        serviceManager.invalidate(service)
    }

    override fun registerService(service: ServiceModel): ServiceModel {
        logger.info("Registering service for ${service.getVipAddress()}.")

        val registeredService = serviceManager.register(service)

        logger.info("The new service got the id ${registeredService.getId()}")

        return registeredService
    }
}