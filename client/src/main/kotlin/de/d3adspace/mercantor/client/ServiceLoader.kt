package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.commons.codec.GsonJaxRSProvider
import de.d3adspace.mercantor.commons.model.ServiceModel
import io.reactivex.Observable
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
open class ServiceLoader(private val mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig) {

    private val client: Client = ClientBuilder.newBuilder().register(GsonJaxRSProvider()).build()

    fun loadServices(vipAddress: String): Observable<List<ServiceModel>> {
        val responseFuture = client.target(mercantorDiscoveryClientConfig.server + "/v1/service/get/{vipAddress}")
                .resolveTemplate("vipAddress", vipAddress)
                .request(MediaType.APPLICATION_JSON)
                .async().get()

        return Observable.fromFuture(responseFuture)
                .map {
                    if (!it.hasEntity()) {
                        return@map emptyList<ServiceModel>()
                    }

                    return@map it.readEntity(Array<ServiceModel>::class.java).asList()
                }
    }
}