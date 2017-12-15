package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.commons.model.ServiceModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class ServiceContainer(private val vipAddress: String, private val serviceLoader: ServiceLoader) {

    private val logger = LoggerFactory.getLogger(ServiceContainer::class.java)

    private var serviceModels: List<ServiceModel> = ArrayList()
    private lateinit var serviceModelListSubject: BehaviorSubject<List<ServiceModel>>

    val services: Observable<List<ServiceModel>>
        get() {
            if (!this::serviceModelListSubject.isInitialized) {
                serviceModelListSubject = BehaviorSubject.create()

                loadServices()

                serviceModelListSubject.onNext(serviceModels)

                startPolling()
            }

            return serviceModelListSubject
        }

    private fun startPolling() {
        logger.info("Starting to poll services from backend for $vipAddress.")

        val observable = Observable.interval(30, TimeUnit.SECONDS).map { fetchServices() }
        observable.subscribe(serviceModelListSubject)
    }

    private fun loadServices() {
        logger.info("Initially loading services for $vipAddress.")

        serviceModels = fetchServices()
    }

    private fun fetchServices(): List<ServiceModel> {
        logger.info("Fetching services for $vipAddress from backend.")

        return serviceLoader.loadServices(vipAddress).blockingLast()
    }
}
