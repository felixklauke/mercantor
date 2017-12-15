package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.commons.codec.GsonJaxRSProvider
import de.d3adspace.mercantor.commons.model.ServiceModel
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class ServiceLoader {

    private val client: Client = ClientBuilder.newBuilder().register(GsonJaxRSProvider()).build()

    fun loadServices(vipAddress: String): List<ServiceModel> {
        val response = client.target("http://localhost:8080/v1/service/get/" + vipAddress)
                .request(MediaType.APPLICATION_JSON).get()

        return response.readEntity(Array<ServiceModel>::class.java).asList()
    }
}