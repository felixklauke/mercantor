package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class ServiceRepositoryImpl : ServiceRepository {

    private val logger = LoggerFactory.getLogger(ServiceRepositoryImpl::class.java)

    private val expirationPublisher: PublishSubject<ServiceModel> = PublishSubject.create()
    private val services: Map<UUID, ServiceModel> = ConcurrentHashMap()
    private val heartbeats: BehaviorSubject<HeartbeatModel> = BehaviorSubject.create()

    override fun getServiceExpiration(): Observable<ServiceModel> {
        return expirationPublisher
    }

    override fun exists(instanceId: UUID): Boolean {
        return services.containsKey(instanceId)
    }

    override fun register(service: ServiceModel) {
        heartbeats.filter { model -> model.instanceId == service.instanceId }
                .timeout(10, TimeUnit.SECONDS)
                .subscribe({ heartbeat ->
                    logger.info("Got heartbeat from ${service.instanceId}.")
                    service.status = heartbeat.status
                }, {
                    expirationPublisher.onNext(service)
                    service.status = ServiceStatus.OUT_OF_SERVICE
                })
    }

    override fun delete(instanceId: UUID) {

    }

    override fun updateStatus(heartbeat: HeartbeatModel) {
        heartbeats.onNext(heartbeat)
    }

    override fun getService(vipAddress: String): List<ServiceModel> {
        return Collections.emptyList()
    }
}