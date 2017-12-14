package de.d3adspace.mercantor.commons.model

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
data class HeartbeatModel(val status: ServiceStatus,
                          val instanceId: UUID)