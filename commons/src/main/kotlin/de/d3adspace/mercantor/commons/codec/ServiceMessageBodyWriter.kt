package de.d3adspace.mercantor.commons.codec

import com.google.gson.GsonBuilder
import de.d3adspace.mercantor.commons.model.service.Service
import java.io.OutputStream
import java.lang.reflect.Type
import javax.ws.rs.Consumes
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyWriter
import javax.ws.rs.ext.Provider

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
class ServiceMessageBodyWriter : MessageBodyWriter<Service> {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun isWriteable(clazz: Class<*>, type: Type, annotations: Array<out Annotation>, mediaType: MediaType): Boolean {
        return Service::class.java.isAssignableFrom(clazz)
    }

    override fun writeTo(service: Service, clazz: Class<*>, type: Type, annotations: Array<out Annotation>, mediaType: MediaType, headers: MultivaluedMap<String, Any>, outputStream: OutputStream) {
        val json = gson.toJson(service)
        outputStream.write(json.toByteArray())
    }
}