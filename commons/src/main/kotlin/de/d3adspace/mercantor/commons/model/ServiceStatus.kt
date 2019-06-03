package de.d3adspace.mercantor.commons.model

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
enum class ServiceStatus {

    /**
     * Describes that a servuce nay ve up and is fully working.
     */
    UP,

    /**
     * Describes that a service is down and not available or requests.
     */
    DOWN,

    /**
     * Describes that a service was marked as out of service because it did not send hearbeats for a while.
     */
    OUT_OF_SERVICE,

    /**
     * Describes that the current state of a service is unknown.
     */
    UNKNOWN,

    /**
     * Describes that a service is currently in warm up time and is not available until it sends an up heartbeat.
     */
    STARTING_UP
}
