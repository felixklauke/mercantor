package de.d3adspace.mercantor.commons.codec

import com.google.gson.GsonBuilder
import java.io.OutputStream
import java.lang.reflect.Type
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyWriter
import javax.ws.rs.ext.Provider

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
open class GsonMessageBodyWriter<ContentType>(private val clazz: Class<out ContentType>) : MessageBodyWriter<ContentType> {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun isWriteable(p0: Class<*>, p1: Type?, p2: Array<out Annotation>?, p3: MediaType?): Boolean {
        return clazz.isAssignableFrom(p0)
    }

    override fun writeTo(p0: ContentType, p1: Class<*>?, p2: Type?, p3: Array<out Annotation>?, p4: MediaType?, p5: MultivaluedMap<String, Any>?, outputStream: OutputStream) {
        val byteArray = gson.toJson(p0).toByteArray()

        outputStream.write(byteArray)
    }
}