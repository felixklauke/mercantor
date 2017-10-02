package de.d3adspace.mercantor.core.task;

import de.d3adspace.mercantor.core.IMercantor;
import de.d3adspace.mercantor.core.registry.Service;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceExpirationChecker implements Runnable {

    private final IMercantor mercantor;

    public ServiceExpirationChecker(IMercantor mercantor) {
        this.mercantor = mercantor;
    }

    @Override
    public void run() {
        for (Service service : mercantor.getServices()) {
            mercantor.checkService(service);
        }
    }
}
