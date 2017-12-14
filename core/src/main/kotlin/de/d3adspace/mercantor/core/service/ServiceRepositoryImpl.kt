package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ServiceRepositoryImpl : ServiceRepository {

    private val heartbeats: BehaviorSubject<HeartbeatModel> = BehaviorSubject.create()
    private val services: Map<UUID, ServiceModel> = ConcurrentHashMap()

    override fun getServiceExpiration(): Observable<ServiceModel> {
        return Observable.create { }
    }

    override fun exists(instanceId: UUID): Boolean {
        return services.containsKey(instanceId)
    }

    override fun register(service: ServiceModel) {

    }

    override fun delete(instanceId: UUID) {

    }

    override fun updateStatus(instanceId: UUID, status: ServiceStatus) {

    }

    override fun getService(vipAddress: String): List<ServiceModel> {
        return Collections.emptyList()
    }
}