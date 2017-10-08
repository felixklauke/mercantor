package de.d3adspace.mercantor.server.resource;

import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.server.service.IServiceManager;
import de.d3adspace.mercantor.shared.path.MercantorPathConstants;
import de.d3adspace.mercantor.shared.transport.IService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Path("")
public class MercantorResource {

    private final MercantorServerConfig mercantorServerConfig;
    private final IServiceManager serviceManager;

    @Inject
    public MercantorResource(MercantorServerConfig mercantorServerConfig, IServiceManager serviceManager) {
        this.mercantorServerConfig = mercantorServerConfig;
        this.serviceManager = serviceManager;
    }

    @GET
    @Path(MercantorPathConstants.GET_BY_ROLE)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServiceByRole(@PathParam("role") String role) {
        IService service = serviceManager.getService(role);
        return service == null ? Response.noContent().build() : Response.ok(service).build();
    }

    @POST
    @Path(MercantorPathConstants.REGISTER)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerService(IService serviceContent) {
        serviceManager.registerService(serviceContent);
        return Response.created(URI.create(mercantorServerConfig.getHost() + ":" + mercantorServerConfig.getPort() + MercantorPathConstants.REGISTER + "/" + serviceContent.getServiceKey())).entity(serviceContent).build();
    }

    @PUT
    @Path(MercantorPathConstants.UPDATE)
    public Response updateService(@PathParam("serviceKey") String serviceKey) {
        serviceManager.updateService(serviceKey);
        return Response.ok().build();
    }

    @DELETE
    @Path(MercantorPathConstants.REMOVE)
    public Response removeService(@PathParam("serviceKey") String serviceKey) {
        serviceManager.removeService(serviceKey);
        return Response.ok().build();
    }
}
