package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.ServiceModel
import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface ServiceManager {

    fun getServices(vipAddress: String): List<ServiceModel>

    fun getService(vipAddress: String): ServiceModel

    fun getServices(): Set<ServiceModel>

    fun invalidate(service: ServiceModel)

    fun invalidate(serviceId: UUID)

    fun register(service: ServiceModel): ServiceModel

    fun handleHeartBeat(heartBeat: HeartBeat)
}