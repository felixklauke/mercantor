package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.commons.codec.GsonJaxRSProvider
import de.d3adspace.mercantor.commons.model.ServiceModel
import io.reactivex.Observable
import org.glassfish.jersey.client.ClientProperties
import org.slf4j.LoggerFactory
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
open class ServiceLoader(private val mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig) {

    companion object {
        private val logger = LoggerFactory.getLogger(ServiceLoader::class.java)
    }

    private val client: Client = ClientBuilder.newBuilder().register(GsonJaxRSProvider()).build()

    init {
        client.property(ClientProperties.CONNECT_TIMEOUT, 1000)
        client.property(ClientProperties.READ_TIMEOUT, 1000)
    }

    fun loadServices(vipAddress: String): Observable<List<ServiceModel>> {
        logger.info("Loading services for $vipAddress.")

        val responseFuture = client.target(mercantorDiscoveryClientConfig.server + "/v1/service/get/{vipAddress}")
                .resolveTemplate("vipAddress", vipAddress)
                .request(MediaType.APPLICATION_JSON)
                .async().get()

        logger.info("Fired request for services of $vipAddress.")

        return Observable.fromFuture(responseFuture)
                .map {
                    if (!it.hasEntity()) {
                        logger.info("The request for the services of $vipAddress returned a response with no entity and no instances.")
                        return@map emptyList<ServiceModel>()
                    }

                    val services = it.readEntity(Array<ServiceModel>::class.java).asList()
                    logger.info("The request for the services of $vipAddress returned a total of ${services.size} instances.")
                    return@map services
                }
    }
}
