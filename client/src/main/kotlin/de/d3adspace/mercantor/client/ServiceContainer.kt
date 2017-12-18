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

    /**
     * The logger that will log all actions regarding this service.
     */
    private val logger = LoggerFactory.getLogger(ServiceContainer::class.java)

    /**
     * All fetched service models.
     */
    private var serviceModels: List<ServiceModel> = ArrayList()

    /**
     * Observable for the services.
     */
    private lateinit var serviceModelListSubject: BehaviorSubject<List<ServiceModel>>

    /**
     * Initialization handling for the services.
     */
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

    /**
     * Start polling for a service set in the background.
     */
    private fun startPolling() {
        logger.info("Starting to poll services from backend for $vipAddress.")

        val observable = Observable.interval(30, TimeUnit.SECONDS).map {
            loadServices()
            return@map serviceModels
        }
        observable.subscribe(serviceModelListSubject)
    }

    /**
     * Load and store the services synchronously.
     */
    private fun loadServices() {
        logger.info("Initially loading services for $vipAddress.")

        serviceModels = fetchServices()
    }

    /**
     * Fetch services from backend synchronously.
     */
    private fun fetchServices(): List<ServiceModel> {
        logger.info("Fetching services for $vipAddress from backend.")

        return getServicesFromBackend().blockingLast()
    }

    /**
     * Get the observable of the services loaded from backend.
     */
    private fun getServicesFromBackend(): Observable<List<ServiceModel>> {
        return serviceLoader.loadServices(vipAddress)
    }
}
