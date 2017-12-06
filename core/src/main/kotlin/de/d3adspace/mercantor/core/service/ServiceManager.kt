package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.core.heartbeat.HeartBeat

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface ServiceManager {

    fun getServices(): Set<Service>

    fun invalidate(service: Service)

    fun register(service: Service)

    fun handleHeartBeat(heartBeat: HeartBeat)
}