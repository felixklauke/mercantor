package de.d3adspace.mercantor.server.rest

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
interface RestManager {

    fun startService()

    fun stopService()

    fun isServiceRunning(): Boolean
}
