package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.ServiceClusterModel
import de.d3adspace.mercantor.commons.model.service.ServiceModel
import java.util.*

/**
 * The central interface that defines how all others can interact with mercantor.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface Mercantor {

    fun registerService(service: ServiceModel): ServiceModel

    fun invalidateService(service: ServiceModel)

    fun getService(vipAddress: String): ServiceModel

    fun handleServiceHeartBeat(heartBeat: HeartBeat)

    fun invalidateService(serviceId: UUID)

    fun getServices(vipAddress: String, limit: Int): ServiceClusterModel
}