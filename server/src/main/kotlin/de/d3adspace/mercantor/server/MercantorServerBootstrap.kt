package de.d3adspace.mercantor.server

import com.google.gson.GsonBuilder
import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import de.d3adspace.mercantor.core.MercantorFactory
import de.d3adspace.mercantor.server.config.MercantorServerConfig
import de.d3adspace.mercantor.server.rest.RestManagerFactory
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * The central bootstrap of the server functionality.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
object MercantorServerBootstrap {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    @JvmStatic
    fun main(args: Array<String>) {
        val config = readConfig()

        val mercantor = MercantorFactory.createMercantor()
        val restManager = RestManagerFactory.createRestManager(mercantor, config)

        val randomUUID = UUID.randomUUID()
        mercantor.registerService(ServiceModel(UUID.randomUUID(), "de.felix-klauke.de", "0.0.0.0", "falafel.de", 2, "Test", ServiceStatus.UP, emptyMap()))
        mercantor.registerService(ServiceModel(randomUUID, "de.felix-klauke.de", "0.0.0.0", "falafel.de", 2, "Test", ServiceStatus.UP, emptyMap()))

        restManager.startService()

        Thread.sleep(3000)
        mercantor.handleHeartbeat(HeartbeatModel(ServiceStatus.UP, randomUUID))
    }

    private fun readConfig(): MercantorServerConfig {
        val configPath = Paths.get("mercantor-server-config.json")
        var serverConfig = MercantorServerConfig("http://0.0.0.0:8080")

        if (!Files.exists(configPath)) {
            Files.createFile(configPath)
            Files.write(configPath, gson.toJson(serverConfig).toByteArray())

            return serverConfig
        }

        Files.newBufferedReader(configPath).use {
            serverConfig = gson.fromJson(it, serverConfig.javaClass)
        }

        return serverConfig
    }
}