package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.core.service.Service

class MercantorImpl : Mercantor {

    override fun getServices(): Set<Service> {
        return emptySet()
    }

    override fun invalidateService(service: Service) {

    }

    override fun registerService(service: Service) {

    }
}