package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.Service
import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface ServiceManager {

    fun getServices(vipAddress: String): List<Service>

    fun getService(vipAddress: String): Service

    fun getServices(): Set<Service>

    fun invalidate(service: Service)

    fun invalidate(serviceId: UUID)

    fun register(service: Service): Service

    fun handleHeartBeat(heartBeat: HeartBeat)
}