package de.d3adspace.mercantor.server.resource

import de.d3adspace.mercantor.commons.model.heartbeat.HeartBeat
import de.d3adspace.mercantor.commons.model.service.Service
import de.d3adspace.mercantor.core.Mercantor
import java.util.concurrent.Executors
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

    private val executor = Executors.newCachedThreadPool()

    @POST
    @Path("/service/register")
    @Consumes(MediaType.APPLICATION_JSON)
    fun registerService(@Suspended requestContext: AsyncResponse, service: Service) {
        executor.execute {
            val registeredService = mercantor.registerService(service)
            val response = Response.ok().entity(registeredService)
            requestContext.resume(response.build())
        }
    }

    @PUT
    @Path("/service/heartbeat")
    @Consumes(MediaType.APPLICATION_JSON)
    fun handleHeartBeat(@Suspended requestContext: AsyncResponse, heartBeat: HeartBeat) {

    }

    @DELETE
    @Path("/service/invalidate/{serviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun invalidateService(@Suspended requestContext: AsyncResponse) {

    }

    @GET
    @Path("/service/get/{vipAddress}")
    fun getService(@Suspended requestContext: AsyncResponse, @PathParam("vipAddress") vipAddress: String) {
        executor.execute {
            val service = mercantor.getService(vipAddress)
            val response = Response.ok().entity(service)
            requestContext.resume(response)
        }
    }
}