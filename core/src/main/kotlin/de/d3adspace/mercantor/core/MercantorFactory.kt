package de.d3adspace.mercantor.core

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
object MercantorFactory {

    fun createMercantor(): Mercantor {
        return MercantorImpl()
    }
}