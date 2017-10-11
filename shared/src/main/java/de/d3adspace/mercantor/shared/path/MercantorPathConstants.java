package de.d3adspace.mercantor.shared.path;

/**
 * Constants showing the paths available for service handling.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorPathConstants {

    /**
     * Path for service registration.
     */
    public static final String REGISTER = "/service";

    /**
     * Path for sending heartbeats.
     */
    public static final String UPDATE = "/service/{serviceKey}";

    /**
     * Path for service removal.
     */
    public static final String REMOVE = "/service/{serviceKey}";

    /**
     * Path for service querying.
     */
    public static final String GET_BY_ROLE = "/service/{role}";
}
