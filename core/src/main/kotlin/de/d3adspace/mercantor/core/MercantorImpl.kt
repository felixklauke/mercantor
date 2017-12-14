package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import de.d3adspace.mercantor.core.service.ServiceRepository
import io.reactivex.functions.Consumer
import org.slf4j.LoggerFactory
import java.util.*

class MercantorImpl(private val serviceRepository: ServiceRepository) : Mercantor {

    private val logger = LoggerFactory.getLogger(MercantorImpl::class.java)

    init {
        serviceRepository.getServiceExpiration().subscribe(this::handleExpiration)
    }

    override fun registerService(service: ServiceModel) {
        logger.info("Registering new instance for vip address ${service.vipAddress}.")

        serviceRepository.register(service)

        logger.info("Registered new instance for vip address ${service.vipAddress} with instance id ${service.instanceId}")
    }

    override fun serviceExists(instanceId: UUID): Boolean {
        return serviceRepository.exists(instanceId)
    }

    override fun deleteService(instanceId: UUID) {
        logger.info("Invalidating instance with id $instanceId.")

        serviceRepository.delete(instanceId)

        logger.info("Invalidated instance with id $instanceId")
    }

    override fun handleHeartbeat(heartbeat: HeartbeatModel) {
        logger.info("Got heartbeat from instance with id ${heartbeat.instanceId}")

        serviceRepository.updateStatus(heartbeat.instanceId, heartbeat.status)
    }

    override fun getService(vipAddress: String): List<ServiceModel> {
        logger.info("Querying instances behind vip address $vipAddress.")

        return serviceRepository.getService(vipAddress).filter { serviceModel ->
            serviceModel.status == ServiceStatus.UP
        }
    }

    private fun handleExpiration(service: ServiceModel) {
        logger.info("The service ${service.instanceId} expired.")

        service.status = ServiceStatus.OUT_OF_SERVICE
    }
}