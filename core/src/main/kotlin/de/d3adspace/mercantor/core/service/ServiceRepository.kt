package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import io.reactivex.Observable
import java.util.*

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
interface ServiceRepository {

    fun register(service: ServiceModel)

    fun delete(instanceId: UUID)

    fun updateStatus(heartbeat: HeartbeatModel)

    fun getService(vipAddress: String): List<ServiceModel>

    fun getServiceExpiration(): Observable<ServiceModel>

    fun exists(instanceId: UUID): Boolean
}
