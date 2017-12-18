package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.commons.codec.GsonJaxRSProvider
import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.glassfish.jersey.client.ClientProperties
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

/**
 * The service agent that will take care of the local registered services.
 *
 * You will have to initialize the flow of the service agent using.
 *
 * @see #registerService() and #startHeartsbeats().
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
class ServiceAgent(private val mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig, private val model: ServiceModel) {

    /**
     * The logger that will log all actions.
     */
    private val logger = LoggerFactory.getLogger(ServiceAgent::class.java)

    /**
     * The client used for network operations.
     */
    private val client: Client = ClientBuilder.newBuilder().register(GsonJaxRSProvider()).build()

    /**
     * The dispose bag for all subscriptions.
     */
    private var compositeDisposable = CompositeDisposable()

    /**
     * Initialize additional client properties.
     */
    init {
        client.property(ClientProperties.CONNECT_TIMEOUT, 1000)
        client.property(ClientProperties.READ_TIMEOUT, 1000)
    }

    /**
     * Register the service against the backend.
     *
     * @return The observable of the id the service got from the backend.
     */
    fun registerService(): Observable<UUID> {
        val responseFuture = client.target(mercantorDiscoveryClientConfig.server + "/v1/service/register")
                .request(MediaType.APPLICATION_JSON)
                .async().post(Entity.json(model))

        return Observable.fromFuture(responseFuture, Schedulers.io()).map {
            val entity = it.readEntity(ServiceModel::class.java)
            return@map entity.instanceId
        }
    }

    /**
     * Let the heart begin beating.
     *
     * The heartbeats will be sent until the service model isn't marked as UP anymore.
     */
    fun startHeartbeats() {
        logger.info("Starting heartbeat background service for ${model.instanceId}.")

        val subscription = Observable.interval(10, TimeUnit.SECONDS)
                .map { sendHeartBeat() }.subscribe()
        compositeDisposable.add(subscription)
    }

    /**
     * Send a single heartbeat.
     */
    private fun sendHeartBeat() {
        logger.info("Sending heartbeat for ${model.instanceId}. Current status: ${model.status}.")

        val model = HeartbeatModel(model.status, model.instanceId)
        val responseFuture = client.target(mercantorDiscoveryClientConfig.server + "/v1/service/heartbeat")
                .request(MediaType.APPLICATION_JSON)
                .async().post(Entity.json(model))

        val subscription = Observable.fromFuture(responseFuture, Schedulers.io())
                .subscribe { logger.info("Sending heartbeat $model successful.") }
        compositeDisposable.add(subscription)
    }

    /**
     * Destroy the service and the registration.
     */
    fun destroy() {
        logger.info("Unregistering service ${model.instanceId}.")

        model.status = ServiceStatus.DOWN

        client.target(mercantorDiscoveryClientConfig.server + "/v1/service/invalidate/{instanceId}")
                .resolveTemplate("instanceId", model.instanceId)
                .request(MediaType.APPLICATION_JSON)
                .async().delete()

        compositeDisposable.dispose()
    }
}