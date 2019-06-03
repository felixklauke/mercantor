package de.d3adspace.mercantor.core.service

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class ServiceRepositoryImplTest {

    private lateinit var serviceRepository: ServiceRepository

    @Before
    fun setUp() {
        serviceRepository = ServiceRepositoryImpl()
    }

    @Test
    fun getServiceExpiration() {
        val serviceExpiration = serviceRepository.getServiceExpiration()

        Assert.assertNotNull(serviceExpiration)
    }

    @Test
    fun exists() {
        val exists = serviceRepository.exists(UUID.randomUUID())

        Assert.assertFalse(exists)
    }

    @Test
    fun register() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun updateStatus() {
    }

    @Test
    fun getService() {
    }
}
