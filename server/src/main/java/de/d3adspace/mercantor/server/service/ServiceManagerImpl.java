package de.d3adspace.mercantor.server.service;

import com.google.common.collect.Maps;
import de.d3adspace.mercantor.server.MercantorServerConstants;
import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.shared.thread.PrefixedThreadFactory;
import de.d3adspace.mercantor.shared.transport.ExtendedServiceModel;
import de.d3adspace.mercantor.shared.transport.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Default implementation of the {@link IServiceManager}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceManagerImpl implements IServiceManager {

    /**
     * The logger to log all actions.
     */
    private final Logger logger = LoggerFactory.getLogger(ServiceManagerImpl.class);

    /**
     * All known services keyed by the role.
     */
    private final Map<String, IService> serviceByRole;

    /**
     * All known services keyed by their unique od.
     */
    private final Map<String, IService> serviceByKey;

    /**
     * All known services with their heart beats.
     */
    private final Map<IService, Long> serviceHeartbeats;

    /**
     * The underlying config.
     */
    private final MercantorServerConfig mercantorServerConfig;

    @Inject
    public ServiceManagerImpl(MercantorServerConfig mercantorServerConfig) {
        this.mercantorServerConfig = mercantorServerConfig;
        this.serviceByRole = Maps.newHashMap();
        this.serviceByKey = Maps.newHashMap();
        this.serviceHeartbeats = Maps.newConcurrentMap();

        startCheckDeadServicesTask();
    }

    /**
     * Start the task that will mark dead services.
     */
    private void startCheckDeadServicesTask() {
        ScheduledExecutorService executorService = Executors
                .newSingleThreadScheduledExecutor(new PrefixedThreadFactory(MercantorServerConstants.WORKER_THREAD_PREFIX));

        executorService.scheduleAtFixedRate(() -> {
            for (Map.Entry<IService, Long> entry : this.serviceHeartbeats.entrySet()) {
                long heartbeat = entry.getValue();
                IService service = entry.getKey();

                if (System.currentTimeMillis() - heartbeat > mercantorServerConfig.getServiceExpirationTimeUnit().toMillis(mercantorServerConfig.getServiceExpiration())) {
                    logger.info("The service {} for role {} at {} is bleeding hard and will be removed!", service.getServiceKey(), service.getRole(), service.getBasePath());

                    removeService(service.getServiceKey().toString());
                }
            }
        }, 0L, mercantorServerConfig.getServiceExpiration() / 2, mercantorServerConfig.getServiceExpirationTimeUnit());
    }

    @Override
    public IService getService(String role) {
        logger.info("Getting service for role {}.", role);

        IService service = serviceByRole.get(role);

        if (service == null) {
            return null;
        }

        return service;
    }

    @Override
    public void registerService(IService service) {
        logger.info("Registering service at {} for role {} with key {}.", service.getBasePath(), service.getRole(), service.getServiceKey());

        serviceByRole.put(service.getRole(), service);
        serviceByKey.put(service.getServiceKey().toString(), service);
        serviceHeartbeats.put(service, System.currentTimeMillis());

        if (service instanceof ExtendedServiceModel) {
            ((ExtendedServiceModel) service).setExpirationTimeUnit(mercantorServerConfig.getServiceExpirationTimeUnit());
            ((ExtendedServiceModel) service).setExpiration(mercantorServerConfig.getServiceExpiration());
        }
    }

    @Override
    public void updateService(String serviceKey) {
        logger.info("Got heartbeat for service {}.", serviceKey);

        serviceHeartbeats.put(serviceByKey.get(serviceKey), System.currentTimeMillis());
    }

    @Override
    public void removeService(String serviceKey) {
        logger.info("Removing service {}.", serviceKey);

        IService service = serviceByKey.get(serviceKey);

        serviceByRole.remove(service.getRole());
        serviceByKey.remove(serviceKey);
        serviceHeartbeats.remove(service);
    }
}
