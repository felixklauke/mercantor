package de.d3adspace.mercantor.server

import de.d3adspace.mercantor.core.MercantorFactory

/**
 * The central bootstrap of the server functionality.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
object MercantorServerBootstrap {

    @JvmStatic
    fun main(args: Array<String>) {
        val mercantor = MercantorFactory.createMercantor()
        
    }
}