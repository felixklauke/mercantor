package de.d3adspace.mercantor.commons.model.service

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
data class ServiceModel(private val vipAddress: String,
                        val serviceName: String,
                        var serviceId: UUID,
                        val serviceHost: String,
                        val servicePort: Int) : Service {

    override fun setId(serviceId: UUID) {
        this.serviceId = serviceId
    }

    override fun getVipAddress(): String = vipAddress

    override fun toString(): String {
        return "ServiceModel(vipAddress='$vipAddress', serviceName='$serviceName', serviceId=$serviceId, serviceHost='$serviceHost', servicePort=$servicePort)"
    }
}