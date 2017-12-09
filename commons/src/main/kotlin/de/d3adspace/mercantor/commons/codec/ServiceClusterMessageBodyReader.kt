package de.d3adspace.mercantor.commons.codec

import de.d3adspace.mercantor.commons.model.service.ServiceClusterModel
import javax.ws.rs.ext.Provider

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@Provider
class ServiceClusterMessageBodyReader : GsonMessageBodyReader<ServiceClusterModel>(ServiceClusterModel::class.java)