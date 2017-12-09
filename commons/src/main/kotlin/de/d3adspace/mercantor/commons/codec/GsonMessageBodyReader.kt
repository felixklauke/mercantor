package de.d3adspace.mercantor.commons.codec

import com.google.gson.GsonBuilder
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.Provider

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
open class GsonMessageBodyReader<ContentType>(private val clazz: Class<out ContentType>) : MessageBodyReader<ContentType> {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun readFrom(p0: Class<ContentType>, p1: Type?, p2: Array<out Annotation>?, p3: MediaType?, p4: MultivaluedMap<String, String>?, p5: InputStream?): ContentType {
        InputStreamReader(p5).use {
            return gson.fromJson(it, clazz) as ContentType
        }
    }

    override fun isReadable(p0: Class<*>, p1: Type?, p2: Array<out Annotation>?, p3: MediaType?): Boolean {
        return clazz.isAssignableFrom(p0)
    }
}