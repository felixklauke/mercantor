package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.commons.model.ServiceModel

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface MercantorDiscoveryClient {

    fun discoverService(vipAddress: String): ServiceModel
}