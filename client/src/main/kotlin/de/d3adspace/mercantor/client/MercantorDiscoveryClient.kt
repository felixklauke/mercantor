package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.commons.model.service.ServiceClusterModel
import de.d3adspace.mercantor.commons.model.service.ServiceModel

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface MercantorDiscoveryClient {

    fun discoverService(vipAddress: String): ServiceModel

    fun discoverServiceCluster(vipAddress: String): ServiceClusterModel
}