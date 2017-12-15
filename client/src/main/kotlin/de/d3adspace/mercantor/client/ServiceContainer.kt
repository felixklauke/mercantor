package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.commons.model.ServiceModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

/**
 * @author Felix Klauke <fklauke></fklauke>@itemis.de>
 */
class ServiceContainer(private val vipAddress: String, private val serviceLoader: ServiceLoader) {

    private var serviceModels: List<ServiceModel> = ArrayList()
    private lateinit var serviceModelListSubject: BehaviorSubject<List<ServiceModel>>

    val services: Observable<List<ServiceModel>>
        get() {
            if (!this::serviceModelListSubject.isInitialized) {
                serviceModelListSubject = BehaviorSubject.create()

                loadServices()
                serviceModelListSubject.onNext(serviceModels)
            }

            return serviceModelListSubject
        }

    private fun loadServices() {
        serviceModels = serviceLoader.loadServices(vipAddress).blockingFirst()

        val observable = Observable.interval(30, TimeUnit.SECONDS)
                .map {
                    return@map serviceLoader.loadServices(vipAddress).blockingLast()
                }
        observable.subscribe(serviceModelListSubject)
    }
}
