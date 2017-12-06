package de.d3adspace.mercantor.server.model

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
data class ServiceModel(val serviceName: String,
                        val serviceId: UUID,
                        val serviceHost: String,
                        val servicePort: Int)