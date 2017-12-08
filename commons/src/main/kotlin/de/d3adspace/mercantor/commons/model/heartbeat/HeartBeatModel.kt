package de.d3adspace.mercantor.commons.model.heartbeat

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
data class HeartBeatModel(val timestamp: Long, val serviceId: UUID) {

    override fun toString(): String {
        return "HeartBeatModel(timestamp=$timestamp, serviceId=$serviceId)"
    }
}

