package de.d3adspace.mercantor.server.resource

import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import de.d3adspace.mercantor.core.MercantorFactory
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import java.util.*
import javax.ws.rs.container.AsyncResponse

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class MercantorServerResourceTest {

    private val serviceModel = ServiceModel(UUID.randomUUID(), "test",
            "0.0.0.0", "felix-klauke.de", 8080, "CoolerService",
            ServiceStatus.UP, emptyMap())
    private val asyncResponse = Mockito.mock(AsyncResponse::class.java)
    private val mercantor = MercantorFactory.createMercantor()
    private val mercantorResource = MercantorServerResource(mercantor)

    @Test
    fun registerService() {
        mercantorResource.registerService(asyncResponse, serviceModel)

        assertTrue(mercantor.serviceExists(serviceModel.instanceId))
        Mockito.verify(asyncResponse).resume(Mockito.any(OutboundJaxrsResponse::class.java))
    }

    @Test
    fun handleHeartBeat() {
        mercantorResource.handleHeartBeat(asyncResponse, HeartbeatModel(ServiceStatus.UP, UUID.randomUUID()))

        mercantor.registerService(serviceModel)

        mercantorResource.handleHeartBeat(asyncResponse, HeartbeatModel(ServiceStatus.UP, serviceModel.instanceId))

        Mockito.verify(asyncResponse, Mockito.times(2)).resume(Mockito.any(OutboundJaxrsResponse::class.java))
    }

    @Test
    fun invalidateService() {
        mercantorResource.invalidateService(asyncResponse, UUID.randomUUID())

        mercantor.registerService(serviceModel)

        mercantorResource.invalidateService(asyncResponse, serviceModel.instanceId)

        Mockito.verify(asyncResponse, Mockito.times(2)).resume(Mockito.any(OutboundJaxrsResponse::class.java))

    }

    @Test
    fun getServices() {
        mercantorResource.getServices(asyncResponse, "test")

        mercantor.registerService(serviceModel)

        mercantorResource.getServices(asyncResponse, "test")

        Mockito.verify(asyncResponse, Mockito.times(2)).resume(Mockito.any(OutboundJaxrsResponse::class.java))
    }
}
