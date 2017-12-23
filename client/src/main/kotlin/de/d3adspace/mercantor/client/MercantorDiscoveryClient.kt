package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.result.DiscoveryResult
import io.reactivex.Observable
import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface MercantorDiscoveryClient {

    fun discoverService(vipAddress: String): Observable<DiscoveryResult>

    fun registerService(vipAddress: String, name: String, host: String, ipAddress: String, port: Int, metaData: Map<String, Any>): Observable<UUID>

    fun unregisterService(instanceId: UUID)

    fun reportInvalidService(instanceId: UUID)
}