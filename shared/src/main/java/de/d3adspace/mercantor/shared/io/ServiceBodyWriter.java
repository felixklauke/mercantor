package de.d3adspace.mercantor.shared.io;

import com.google.gson.Gson;
import de.d3adspace.mercantor.shared.transport.IService;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ServiceBodyWriter implements MessageBodyWriter<IService> {

    private final Gson gson;

    @Inject
    public ServiceBodyWriter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return IService.class.isAssignableFrom(aClass);
    }

    @Override
    public void writeTo(IService service, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        outputStream.write(gson.toJson(service).getBytes());
        outputStream.flush();
    }
}
