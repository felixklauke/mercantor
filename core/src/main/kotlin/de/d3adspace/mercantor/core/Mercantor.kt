package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.core.heartbeat.HeartBeat
import de.d3adspace.mercantor.core.service.Service

/**
 * The central interface that defines how all others can interact with mercantor.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface Mercantor {

    fun registerService(service: Service): Service

    fun invalidateService(service: Service)

    fun getService(vipAddress: String): Service

    fun handleServiceHeartBeat(heartBeat: HeartBeat)
}