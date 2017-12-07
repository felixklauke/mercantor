package de.d3adspace.mercantor.server.rest

import de.d3adspace.mercantor.core.Mercantor
import de.d3adspace.mercantor.server.application.MercantorServerApplication
import de.d3adspace.mercantor.server.config.MercantorServerConfig
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class JerseyRestManager(private val mercantor: Mercantor, private val config: MercantorServerConfig) : RestManager {

    private lateinit var httpServer: HttpServer

    override fun startService() {
        val uri = URI.create(config.restURI)

        val resourceConfig = ResourceConfig.forApplication(MercantorServerApplication(mercantor))
        httpServer = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig)
    }

    override fun stopService() {
        httpServer.shutdown()
    }

    override fun isServiceRunning(): Boolean = httpServer.isStarted
}