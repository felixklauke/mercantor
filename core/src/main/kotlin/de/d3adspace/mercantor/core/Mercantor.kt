package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import java.util.*

/**
 * The central interface that defines how all others can interact with mercantor.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface Mercantor {

    fun registerService(service: ServiceModel)

    fun deleteService(instanceId: UUID)

    fun handleHeartbeat(heartbeat: HeartbeatModel)

    fun getService(vipAddress: String): List<ServiceModel>

    fun serviceExists(instanceId: UUID): Boolean
}