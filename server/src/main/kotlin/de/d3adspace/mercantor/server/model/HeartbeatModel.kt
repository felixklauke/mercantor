package de.d3adspace.mercantor.server.model

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
data class HeartbeatModel(val timestamp: Long, val serviceId: UUID)