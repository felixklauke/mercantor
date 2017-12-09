package de.d3adspace.mercantor.server.resource

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.ServiceModel
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
    fun handleHeartBeat(@Suspended requestContext: AsyncResponse, heartBeat: HeartBeat) {
        mercantor.handleServiceHeartBeat(heartBeat)

        val response = Response.ok().build()
        requestContext.resume(response)
    }

    @DELETE
    @Path("/service/invalidate/{serviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    fun invalidateService(@Suspended requestContext: AsyncResponse, @PathParam("serviceId") serviceId: String) {
        val uuid = UUID.fromString(serviceId)
        mercantor.invalidateService(uuid)

        val response = Response.ok().build()
        requestContext.resume(response)
    }

    @GET
    @Path("/service/get/{vipAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    fun getServices(@Suspended requestContext: AsyncResponse, @PathParam("vipAddress") vipAddress: String, @QueryParam("limit") limit: Int) {
        val response: Response

        response = if (limit == 1 || limit == 0) {
            val service = mercantor.getService(vipAddress)
            Response.ok().entity(service).build()
        } else {
            val services = mercantor.getServices(vipAddress, limit)
            Response.ok().entity(services).build()
        }

        requestContext.resume(response)
    }
}