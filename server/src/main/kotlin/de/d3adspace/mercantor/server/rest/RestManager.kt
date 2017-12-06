package de.d3adspace.mercantor.server.rest

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface RestManager {

    fun startService()

    fun stopService()

    fun isServiceRunning(): Boolean
}