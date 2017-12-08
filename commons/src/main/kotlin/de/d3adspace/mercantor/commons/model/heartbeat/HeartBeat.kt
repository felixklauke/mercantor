package de.d3adspace.mercantor.commons.model.heartbeat

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface HeartBeat {

    fun getClientTimeStamp(): Long

    fun getServiceId(): UUID
}