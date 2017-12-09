package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorClientConfig
import de.d3adspace.mercantor.commons.codec.ServiceClusterMessageBodyReader
import de.d3adspace.mercantor.commons.codec.ServiceClusterMessageBodyWriter
import de.d3adspace.mercantor.commons.codec.ServiceMessageBodyReader
import de.d3adspace.mercantor.commons.codec.ServiceMessageBodyWriter
import de.d3adspace.mercantor.commons.model.service.ServiceClusterModel
import de.d3adspace.mercantor.commons.model.service.ServiceModel
import java.nio.ByteBuffer
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

class MercantorDiscoveryClientImpl(val mercantorClientConfig: MercantorClientConfig) : MercantorDiscoveryClient {

    private val client = ClientBuilder.newBuilder()
            .register(ServiceMessageBodyWriter())
            .register(ServiceMessageBodyReader())
            .register(ServiceClusterMessageBodyWriter())
            .register(ServiceClusterMessageBodyReader())
            .build()

    private lateinit var getServiceTarget: WebTarget

    override fun initializeWebTargets() {
        getServiceTarget = client.target(mercantorClientConfig.serverURL + "/v1/service/get/{vipAddress}")
    }

    override fun discoverServiceCluster(vipAddress: String): ServiceClusterModel {
        return getServiceTarget.resolveTemplate("vipAddress", vipAddress)
                .queryParam("limit", 10)
                .request(MediaType.APPLICATION_JSON)
                .get<ServiceClusterModel>(ServiceClusterModel::class.java)
    }

    override fun discoverService(vipAddress: String): ServiceModel {
        return getServiceTarget.resolveTemplate("vipAddress", vipAddress)
                .request(MediaType.APPLICATION_JSON)
                .get<ServiceModel>(ServiceModel::class.java)
    }
}