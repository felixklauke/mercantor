package de.d3adspace.mercantor.commons.codec

import com.google.gson.GsonBuilder
import de.d3adspace.mercantor.commons.model.service.Service
import de.d3adspace.mercantor.commons.model.service.ServiceModel
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.Provider

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
class ServiceMessageBodyReader : MessageBodyReader<Service> {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun isReadable(clazz: Class<*>, type: Type, annotations: Array<out Annotation>, mediaType: MediaType): Boolean {
        return Service::class.java.isAssignableFrom(clazz)
    }

    override fun readFrom(clazz: Class<Service>, type: Type, annotations: Array<out Annotation>, mediaType: MediaType, headers: MultivaluedMap<String, String>, inputStream: InputStream): Service {
        InputStreamReader(inputStream).use {
            return gson.fromJson(it, ServiceModel::class.java)
        }
    }
}