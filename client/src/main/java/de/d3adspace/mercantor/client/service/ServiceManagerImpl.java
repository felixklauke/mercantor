package de.d3adspace.mercantor.client.service;

import com.google.common.collect.Maps;
import de.d3adspace.mercantor.client.MercantorClientConstants;
import de.d3adspace.mercantor.client.config.MercantorClientConfig;
import de.d3adspace.mercantor.shared.io.ServiceBodyReader;
import de.d3adspace.mercantor.shared.io.ServiceBodyWriter;
import de.d3adspace.mercantor.shared.path.MercantorPathConstants;
import de.d3adspace.mercantor.shared.thread.PrefixedThreadFactory;
import de.d3adspace.mercantor.shared.transport.ExtendedServiceModel;
import de.d3adspace.mercantor.shared.transport.IService;
import de.d3adspace.mercantor.shared.transport.ServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
     * The weg target that will be used to register a new service.
     */
    private final WebTarget targetRegister;

    /**
     * The web target that will be used to update a service via sending a heartbeat.
     */
    private final WebTarget targetUpdate;

    /**
     * The web target that will be used to unregister a service.
     */
    private final WebTarget targetRemove;

    /**
     * The web target that will be used to query for a service.
     */
    private final WebTarget targetGetByRole;

    /**
     * The executor service for the service heart beating tasks.
     */
    private final ScheduledExecutorService executorService;

    /**
     * The futures that represent the updating tasks.
     */
    private final Map<IService, Future> currentRegisteredServices;

    @Inject
    public ServiceManagerImpl(MercantorClientConfig clientConfig, ServiceBodyReader serviceBodyReader, ServiceBodyWriter serviceBodyWriter) {
        Client client = ClientBuilder.newBuilder().register(serviceBodyReader).register(serviceBodyWriter).build();

        String serverAddress = clientConfig.getServerHost() + ":" + clientConfig.getServerPort();
        targetRegister = client.target(UriBuilder.fromUri(serverAddress + MercantorPathConstants.REGISTER));
        targetUpdate = client.target(UriBuilder.fromUri(serverAddress + MercantorPathConstants.UPDATE));
        targetRemove = client.target(UriBuilder.fromUri(serverAddress + MercantorPathConstants.REMOVE));
        targetGetByRole = client.target(UriBuilder.fromUri(serverAddress + MercantorPathConstants.GET_BY_ROLE));

        this.executorService = Executors.newScheduledThreadPool(4, new PrefixedThreadFactory(MercantorClientConstants.SERVICE_THREAD_PREFIX));
        this.currentRegisteredServices = Maps.newConcurrentMap();
    }

    @Override
    public IService registerService(String basePath, String role) {
        IService service = new ServiceModel(basePath, role);

        logger.info("Registering service at {} with role {}.", basePath, role);

        return registerService(Entity.json(service));
    }

    /**
     * Register a service that is serialized in the given entity.
     *
     * @param entity The entity.
     * @return The registered service.
     */
    private IService registerService(Entity entity) {
        Response response = targetRegister.request(MediaType.APPLICATION_JSON).post(entity);
        ExtendedServiceModel serviceModel = (ExtendedServiceModel) response.readEntity(IService.class);

        logger.info("Registered service with key {} at {} with role {} and expiration {} {}.", serviceModel.getServiceKey(), serviceModel.getBasePath(), serviceModel.getRole(), serviceModel.getExpiration(),
                serviceModel.getExpirationTimeUnit().toString());

        Future<?> future = startRefreshingService(serviceModel, serviceModel.getExpiration(), serviceModel.getExpirationTimeUnit());
        currentRegisteredServices.put(serviceModel, future);

        return serviceModel;
    }

    /**
     * Start the task that will send heartbets.
     *
     * @param serviceModel The service to update.
     * @param expiration The interval of sending heartbeats.
     * @param expirationTimeUnit The time unit of the expiration time.
     *
     * @return The future of the updating tasks.
     */
    private Future<?> startRefreshingService(final IService serviceModel, long expiration, TimeUnit expirationTimeUnit) {
        logger.info("Starting refreshing service with key {} with {}{}", serviceModel.getServiceKey(), expiration, expirationTimeUnit.toString());

        return this.executorService.scheduleAtFixedRate(() -> updateService(serviceModel), expiration / 2, expiration, expirationTimeUnit);
    }

    @Override
    public boolean removeService(IService service) {
        logger.info("Removing service with key {} at {} for role {}.", service.getServiceKey(), service.getBasePath(), service.getRole());

        Future<?> future = currentRegisteredServices.get(service);
        future.cancel(true);
        currentRegisteredServices.remove(service);

        Response response = targetRemove.resolveTemplate("serviceKey", service.getServiceKey().toString()).request(MediaType.APPLICATION_JSON).delete();
        return response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL);
    }

    @Override
    public IService getService(String role) {
        logger.info("Getting service for role: " + role);

        Response response = targetGetByRole.resolveTemplate("role", role).request(MediaType.APPLICATION_JSON).get();
        return response.readEntity(IService.class);
    }

    /**
     * Send a heartbeat for the given service.
     *
     * @param service The service.
     */
    private void updateService(IService service) {
        logger.info("Updating service with key {} at {} for role {}.", service.getServiceKey(), service.getBasePath(), service.getRole());

        targetUpdate.resolveTemplate("serviceKey", service.getServiceKey().toString()).request(MediaType.APPLICATION_JSON).put(Entity.text(""));
    }
}
