package de.d3adspace.mercantor.core.service

import de.d3adspace.mercantor.core.heartbeat.HeartBeat

class ServiceManagerImpl : ServiceManager {

    override fun getService(vipAddress: String): Service {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getServices(vipAddress: String): Set<Service> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleHeartBeat(heartBeat: HeartBeat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getServices(): Set<Service> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun invalidate(service: Service) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun register(service: Service): Service {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}