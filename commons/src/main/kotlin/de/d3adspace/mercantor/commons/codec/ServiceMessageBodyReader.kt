package de.d3adspace.mercantor.commons.codec


import de.d3adspace.mercantor.commons.model.service.ServiceModel
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.ext.Provider

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
class ServiceMessageBodyReader : GsonMessageBodyReader<ServiceModel>(ServiceModel::class.java)