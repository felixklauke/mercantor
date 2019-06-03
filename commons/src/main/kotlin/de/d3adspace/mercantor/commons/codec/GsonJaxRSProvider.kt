package de.d3adspace.mercantor.commons.codec

import com.google.gson.GsonBuilder
import java.io.*
import java.lang.reflect.Type
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.MessageBodyWriter
import javax.ws.rs.ext.Provider

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GsonJaxRSProvider : MessageBodyWriter<Any>, MessageBodyReader<Any> {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun isReadable(type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType) = true

    @Throws(IOException::class, WebApplicationException::class)
    override fun readFrom(type: Class<Any>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType, httpHeaders: MultivaluedMap<String, String>, entityStream: InputStream): Any {
        InputStreamReader(entityStream).use { inputStreamReader ->
            return gson.fromJson(inputStreamReader, genericType)
        }
    }

    override fun isWriteable(type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType) = true

    override fun getSize(o: Any?, type: Class<*>?, genericType: Type?, annotations: Array<Annotation>?, mediaType: MediaType?): Long = -1

    @Throws(IOException::class, WebApplicationException::class)
    override fun writeTo(o: Any, type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType, httpHeaders: MultivaluedMap<String, Any>, entityStream: OutputStream) {
        OutputStreamWriter(entityStream).use { outputStreamWriter ->
            gson.toJson(o, outputStreamWriter)
        }
    }
}
