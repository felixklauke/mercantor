package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorClientConfig
import de.d3adspace.mercantor.commons.codec.GsonJaxRSProvider
import de.d3adspace.mercantor.commons.model.service.ServiceClusterModel
import de.d3adspace.mercantor.commons.model.service.ServiceModel
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

class MercantorDiscoveryClientImpl(val mercantorClientConfig: MercantorClientConfig) : MercantorDiscoveryClient {

    private val client = ClientBuilder.newBuilder()
            .register(GsonJaxRSProvider())
            .build()

    private lateinit var getServiceTarget: WebTarget

    override fun initializeWebTargets() {
        getServiceTarget = client.target(mercantorClientConfig.serverURL + "/v1/service/get/{vipAddress}")
    }

    override fun discoverServiceCluster(vipAddress: String): ServiceClusterModel {
        return discoverServiceCluster(vipAddress, 10)
    }

    override fun discoverService(vipAddress: String): ServiceModel {
        return getServiceTarget.resolveTemplate("vipAddress", vipAddress)
                .request(MediaType.APPLICATION_JSON)
                .get<ServiceModel>(ServiceModel::class.java)
    }

    override fun discoverServiceCluster(vipAddress: String, limit: Int): ServiceClusterModel {
        return getServiceTarget.resolveTemplate("vipAddress", vipAddress)
                .queryParam("limit", limit)
                .request(MediaType.APPLICATION_JSON)
                .get<ServiceClusterModel>(ServiceClusterModel::class.java)
    }
}