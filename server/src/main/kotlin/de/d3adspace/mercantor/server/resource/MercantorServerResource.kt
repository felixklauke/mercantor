package de.d3adspace.mercantor.server.resource

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.Service
import de.d3adspace.mercantor.core.Mercantor
import org.glassfish.jersey.server.ManagedAsync
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
    fun registerService(@Suspended requestContext: AsyncResponse, service: Service) {
        val registeredService = mercantor.registerService(service)
        val response = Response.ok().entity(registeredService).build()
        requestContext.resume(response)
    }

    @PUT
    @Path("/service/heartbeat")
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    fun handleHeartBeat(@Suspended requestContext: AsyncResponse, heartBeat: HeartBeat) {

    }

    @DELETE
    @Path("/service/invalidate/{serviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    fun invalidateService(@Suspended requestContext: AsyncResponse) {

    }

    @GET
    @Path("/service/get/{vipAddress}")
    @ManagedAsync
    fun getService(@Suspended requestContext: AsyncResponse, @PathParam("vipAddress") vipAddress: String) {
        val service = mercantor.getService(vipAddress)
        val response = Response.ok().entity(service).build()
        requestContext.resume(response)
    }
}