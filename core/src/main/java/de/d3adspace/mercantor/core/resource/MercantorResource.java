/*
 * Copyright (c) 2017 Felix Klauke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.d3adspace.mercantor.core.resource;

import de.d3adspace.mercantor.core.IMercantor;
import de.d3adspace.mercantor.core.registry.Service;
import de.d3adspace.mercantor.core.registry.ServiceRegistry;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Resource for Jersey.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
@Path("/v1")
public class MercantorResource {

    /**
     * The mercantor instance used for interacting with services.
     */
    private final IMercantor mercantor;

    /**
     * Create a new mercantor resource by its underlying mercantor instance.
     *
     * @param mercantor The mercantor.
     */
    public MercantorResource(IMercantor mercantor) {
        this.mercantor = mercantor;
    }

    @DELETE
    @Path("/service/{serviceKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteService(@PathParam("serviceKey") String serviceKey) {
        if (!ServiceRegistry.hasService(serviceKey)) {
            return Response.status(204).build();
        }

        mercantor.removeService(serviceKey);
        return Response.ok().build();
    }

    @GET
    @Path("/service/{serviceKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServiceByKey(@PathParam("serviceKey") String serviceKey) {
        Service service = mercantor.getService(serviceKey);

        if (service == null) {
            return Response.noContent().build();
        }

        return Response.ok(service).build();
    }

    @POST
    @Path("/service")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerService(@Context UriInfo uriInfo, String content) {
        Service service = mercantor.createService(content);
        return Response.created(URI.create(uriInfo.getPath() + "/" + service.getServiceKey())).build();
    }
}
