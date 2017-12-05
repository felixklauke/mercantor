package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.core.service.Service

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
interface Mercantor {

    fun registerService(service: Service)
}