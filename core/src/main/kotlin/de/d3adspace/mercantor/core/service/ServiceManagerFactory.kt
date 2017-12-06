package de.d3adspace.mercantor.core.service

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
object ServiceManagerFactory {

    fun createServiceManager(): ServiceManager {
        return ServiceManagerImpl()
    }
}