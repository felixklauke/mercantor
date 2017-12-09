package de.d3adspace.mercantor.commons.model.service

class ServiceClusterModel(private val services: List<ServiceModel>) {

    fun getServices(): List<ServiceModel> = services

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceClusterModel

        if (services != other.services) return false

        return true
    }

    override fun hashCode(): Int {
        return services.hashCode()
    }

    override fun toString(): String {
        return "ServiceClusterImpl(services=$services)"
    }
}