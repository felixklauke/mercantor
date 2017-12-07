package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.core.heartbeat.HeartBeat

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface ServiceManager {

    fun getServices(vipAddress: String): Set<Service>

    fun getService(vipAddress: String): Service

    fun getServices(): Set<Service>

    fun invalidate(service: Service)

    fun register(service: Service): Service

    fun handleHeartBeat(heartBeat: HeartBeat)
}