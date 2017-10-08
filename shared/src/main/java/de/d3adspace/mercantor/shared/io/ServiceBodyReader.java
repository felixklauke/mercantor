package de.d3adspace.mercantor.shared.io;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import de.d3adspace.mercantor.shared.transport.ExtendedServiceModel;
import de.d3adspace.mercantor.shared.transport.IService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceBodyReader implements MessageBodyReader<IService> {

    private final Gson gson;

    @Inject
    public ServiceBodyReader(Gson gson) {
        this.gson = gson;
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return IService.class.isAssignableFrom(aClass);
    }

    @Override
    public IService readFrom(Class<IService> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        String serviceContents = CharStreams.toString(new InputStreamReader(inputStream));
        return gson.fromJson(serviceContents, ExtendedServiceModel.class);
    }
}
