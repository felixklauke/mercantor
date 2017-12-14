package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import io.reactivex.Observable
import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface ServiceRepository {

    fun register(service: ServiceModel)

    fun delete(instanceId: UUID)

    fun updateStatus(instanceId: UUID, status: ServiceStatus)

    fun getService(vipAddress: String): List<ServiceModel>

    fun getServiceExpiration(): Observable<ServiceModel>

    fun exists(instanceId: UUID): Boolean
}