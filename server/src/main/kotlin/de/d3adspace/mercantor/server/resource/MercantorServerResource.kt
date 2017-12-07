package de.d3adspace.mercantor.server.resource

import de.d3adspace.mercantor.core.Mercantor
import de.d3adspace.mercantor.server.model.HeartbeatModel
import de.d3adspace.mercantor.server.model.ServiceModel
import javax.ws.rs.*
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.MediaType

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
class MercantorServerResource(private val mercantor: Mercantor) {

    @POST
    @Path("/service/register")
    @Consumes(MediaType.APPLICATION_JSON)
    fun registerService(@Suspended response: AsyncResponse, serviceModel: ServiceModel) {

    }

    @PUT
    @Path("/service/heartbeat")
    @Consumes(MediaType.APPLICATION_JSON)
    fun handleHeartBeat(@Suspended response: AsyncResponse, heartbeatModel: HeartbeatModel) {

    }

    @DELETE
    @Path("/service/invalidate/{serviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun invalidateService(@Suspended response: AsyncResponse) {

    }

    @GET
    @Path("/service/get/{vipAddress}")
    fun getService(@Suspended response: AsyncResponse) {

    }
}