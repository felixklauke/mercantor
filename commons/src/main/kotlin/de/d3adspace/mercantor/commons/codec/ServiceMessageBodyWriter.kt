package de.d3adspace.mercantor.commons.codec

import de.d3adspace.mercantor.commons.model.service.ServiceModel
import javax.ws.rs.Consumes
import javax.ws.rs.core.MediaType
import javax.ws.rs.ext.Provider

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
class ServiceMessageBodyWriter : GsonMessageBodyWriter<ServiceModel>(ServiceModel::class.java)