package de.d3adspace.mercantor.commons.model

import java.util.*

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
data class ServiceModel(var instanceId: UUID = UUID.randomUUID(),
                        val vipAddress: String = "de.d3adspace.service.invalid",
                        val ipAddress: String = "0.0.0.0",
                        val hostName: String = "localhost",
                        val port: Int = -1,
                        val name: String = "exampleService",
                        var status: ServiceStatus = ServiceStatus.UNKNOWN,
                        val metaData: Map<String, Any> = HashMap())
