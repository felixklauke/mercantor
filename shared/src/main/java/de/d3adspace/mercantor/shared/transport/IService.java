package de.d3adspace.mercantor.shared.transport;

import java.util.UUID;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IService {

    UUID getServiceKey();

    String getBasePath();

    String getRole();
}
