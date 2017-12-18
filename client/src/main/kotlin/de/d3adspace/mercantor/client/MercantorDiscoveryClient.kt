package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.commons.model.ServiceModel
import io.reactivex.Observable
import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface MercantorDiscoveryClient {

    fun discoverService(vipAddress: String): ServiceModel

    fun registerService(vipAddress: String, name: String, host: String, ipAddress: String, port: Int, metaData: Map<String, Any>): Observable<UUID>

    fun unregisterService(instanceId: UUID)
}