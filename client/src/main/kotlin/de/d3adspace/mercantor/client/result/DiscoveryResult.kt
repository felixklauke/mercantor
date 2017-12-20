package de.d3adspace.mercantor.client.result

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class DiscoveryResult(val instanceId: UUID, val hostName: String, val ipAddress: String, val port: Int) {

    override fun toString(): String {
        return "DiscoveryResult(instanceId=$instanceId, hostName='$hostName', ipAddress='$ipAddress', port=$port)"
    }
}