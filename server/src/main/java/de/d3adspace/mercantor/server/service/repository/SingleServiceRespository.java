package de.d3adspace.mercantor.server.service.repository;

import de.d3adspace.mercantor.server.exception.IllegalServiceRegisteringException;
import de.d3adspace.mercantor.shared.transport.IService;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class SingleServiceRespository extends AbstractServiceRepository {

    @Override
    public IService getServiceByRole(String role) {
        return null;
    }

    @Override
    public void registerService(IService service) throws IllegalServiceRegisteringException {
        if (getServiceByRole(service.getRole()) != null) {
            throw new IllegalServiceRegisteringException("There is already an service for the role " + service.getRole());
        }

        super.registerService(service);
    }
}
