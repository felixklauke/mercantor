package de.d3adspace.mercantor.server.resource

import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.core.Mercantor
import org.glassfish.jersey.server.ManagedAsync
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
class MercantorServerResource(private val mercantor: Mercantor) {

    @POST
    @Path("/service/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    fun registerService(@Suspended requestContext: AsyncResponse, service: ServiceModel) {
        val registeredService = mercantor.registerService(service)
        val response = Response.ok().entity(registeredService).build()
        requestContext.resume(response)
    }

    @PUT
    @Path("/service/heartbeat")
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    fun handleHeartBeat(@Suspended requestContext: AsyncResponse, heartBeat: HeartbeatModel) {
        val instanceId = heartBeat.instanceId

        if (!mercantor.serviceExists(instanceId)) {
            val response = Response.status(Response.Status.INTERNAL_SERVER_ERROR.statusCode, "There is no instance with the id $instanceId.").build()
            requestContext.resume(response)
            return
        }

        mercantor.handleHeartbeat(heartBeat)
        val response = Response.ok().build()
        requestContext.resume(response)
    }

    @DELETE
    @Path("/service/invalidate/{instanceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    fun invalidateService(@Suspended requestContext: AsyncResponse, @PathParam("instanceId") instanceId: String) {
        val uuid = UUID.fromString(instanceId)
        mercantor.deleteService(uuid)

        val response = Response.ok().build()
        requestContext.resume(response)
    }

    @GET
    @Path("/service/get/{vipAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    fun getServices(@Suspended requestContext: AsyncResponse, @PathParam("vipAddress") vipAddress: String) {
        val services = mercantor.getService(vipAddress)

        if (services.isEmpty()) {
            val response = Response.status(Response.Status.NOT_FOUND).build()
            requestContext.resume(response)
            return
        }

        val response = Response.ok().entity(services).build()
        requestContext.resume(response)
    }
}