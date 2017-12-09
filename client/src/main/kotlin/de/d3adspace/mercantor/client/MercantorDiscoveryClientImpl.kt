package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorClientConfig
import de.d3adspace.mercantor.commons.model.service.ServiceClusterModel
import de.d3adspace.mercantor.commons.model.service.ServiceModel

class MercantorDiscoveryClientImpl(mercantorClientConfig: MercantorClientConfig) : MercantorDiscoveryClient {

    override fun discoverServiceCluster(vipAddress: String): ServiceClusterModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun discoverService(vipAddress: String): ServiceModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}