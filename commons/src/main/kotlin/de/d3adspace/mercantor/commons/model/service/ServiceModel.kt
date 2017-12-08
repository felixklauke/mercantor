package de.d3adspace.mercantor.commons.model.service

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
data class ServiceModel(private val vipAddress: String,
                        val serviceName: String,
                        val serviceHost: String,
                        val servicePort: Int) : Service {

    private var serviceId: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")

    override fun setId(serviceId: UUID) {
        this.serviceId = serviceId
    }

    override fun getVipAddress(): String = vipAddress

    override fun toString(): String {
        return "ServiceModel(vipAddress='$vipAddress', serviceName='$serviceName', serviceId=$serviceId, serviceHost='$serviceHost', servicePort=$servicePort)"
    }
}