package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.commons.codec.GsonJaxRSProvider
import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import io.reactivex.Observable
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
 * @author Felix Klauke <fklauke@itemis.de>
 */
class ServiceAgent(private val mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig, private val model: ServiceModel) {

    private val logger = LoggerFactory.getLogger(ServiceAgent::class.java)
    private val client: Client = ClientBuilder.newBuilder().register(GsonJaxRSProvider()).build()

    init {
        client.property(ClientProperties.CONNECT_TIMEOUT, 1000)
        client.property(ClientProperties.READ_TIMEOUT, 1000)
    }

    fun registerService(): Observable<UUID> {
        val responseFuture = client.target(mercantorDiscoveryClientConfig.server + "/v1/service/register")
                .request(MediaType.APPLICATION_JSON)
                .async().post(Entity.json(model))

        return Observable.fromFuture(responseFuture, Schedulers.io()).map {
            val entity = it.readEntity(ServiceModel::class.java)
            return@map entity.instanceId
        }
    }

    fun startHeartbeats() {
        Observable.interval(30, TimeUnit.SECONDS)
                .takeUntil { model.status != ServiceStatus.UP }
                .map { sendHeartBeat() }
    }

    private fun sendHeartBeat() {
        logger.info("Sending heartbeat for ${model.instanceId}.")

        val model = HeartbeatModel(ServiceStatus.UP, model.instanceId)
        val responseFuture = client.target(mercantorDiscoveryClientConfig.server + "/v1/service/heartbeat")
                .request(MediaType.APPLICATION_JSON)
                .async().post(Entity.json(model))

        Observable.fromFuture(responseFuture, Schedulers.io())
                .subscribe { logger.info("Sending heartbeat $model successful.") }
    }

    fun destroy() {
        model.status = ServiceStatus.DOWN
    }
}