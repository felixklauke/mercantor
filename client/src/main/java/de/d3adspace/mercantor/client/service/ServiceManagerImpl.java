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
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceManagerImpl implements IServiceManager {

    private final Logger logger = LoggerFactory.getLogger(ServiceManagerImpl.class);

    private final WebTarget targetRegister;
    private final WebTarget targetUpdate;
    private final WebTarget targetRemove;
    private final WebTarget targetGetByRole;

    private final ScheduledExecutorService executorService;

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

    private IService registerService(Entity entity) {
        Response response = targetRegister.request(MediaType.APPLICATION_JSON).post(entity);
        ExtendedServiceModel serviceModel = (ExtendedServiceModel) response.readEntity(IService.class);

        logger.info("Registered service with key {} at {} with role {} and expiration {} {}.", serviceModel.getServiceKey(), serviceModel.getBasePath(), serviceModel.getRole(), serviceModel.getExpiration(),
                serviceModel.getExpirationTimeUnit().toString());

        Future<?> future = startRefreshingService(serviceModel, serviceModel.getExpiration(), serviceModel.getExpirationTimeUnit());
        currentRegisteredServices.put(serviceModel, future);

        return serviceModel;
    }

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

    private void updateService(IService service) {
        logger.info("Updating service with key {} at {} for role {}.", service.getServiceKey(), service.getBasePath(), service.getRole());

        targetUpdate.resolveTemplate("serviceKey", service.getServiceKey().toString()).request(MediaType.APPLICATION_JSON).put(Entity.text(""));
    }
}
