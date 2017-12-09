package de.d3adspace.mercantor.commons.model.service

import java.util.*

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
data class ServiceModel(private val vipAddress: String,
                        val serviceName: String,
                        val serviceHost: String,
                        val servicePort: Int) {

    private var serviceId: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")

    fun setId(serviceId: UUID) {
        this.serviceId = serviceId
    }

    fun getVipAddress(): String = vipAddress

    override fun toString(): String {
        return "ServiceModel(vipAddress='$vipAddress', serviceName='$serviceName', serviceId=$serviceId, serviceHost='$serviceHost', servicePort=$servicePort)"
    }

    fun getId(): UUID = serviceId

    override fun hashCode(): Int {
        var result = vipAddress.hashCode()
        result = 31 * result + serviceName.hashCode()
        result = 31 * result + serviceHost.hashCode()
        result = 31 * result + servicePort
        result = 31 * result + serviceId.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceModel

        if (vipAddress != other.vipAddress) return false
        if (serviceName != other.serviceName) return false
        if (serviceHost != other.serviceHost) return false
        if (servicePort != other.servicePort) return false
        if (serviceId != other.serviceId) return false

        return true
    }
}