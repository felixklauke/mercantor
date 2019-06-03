package de.d3adspace.mercantor.commons.model

import java.util.*

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
data class HeartbeatModel(val status: ServiceStatus,
                          val instanceId: UUID)
