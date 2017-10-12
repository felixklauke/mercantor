package de.d3adspace.mercantor.client;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.d3adspace.mercantor.client.config.MercantorClientConfig;
import de.d3adspace.mercantor.client.module.MercantorClientModule;
import de.d3adspace.mercantor.client.service.IServiceManager;
import de.d3adspace.mercantor.shared.thread.PrefixedThreadFactory;
import de.d3adspace.mercantor.shared.transport.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.Executors;

/**
 * The default implementation of the {@link IMercantorClient}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientImpl implements IMercantorClient {

    /**
     * The logger to log all actions.
     */
    private final Logger logger;

    /**
     * The executor service needed to create the async operations.
     */
    private final ListeningScheduledExecutorService executorService;

    @Inject
    private IServiceManager serviceManager;

    /**
     * Create a new client by its config. Used by the {@link MercantorClientFactory}.
     *
     * @param mercantorClientConfig The underlying config.
     */
    MercantorClientImpl(MercantorClientConfig mercantorClientConfig) {
        this.logger = LoggerFactory.getLogger(MercantorClientImpl.class);
        this.executorService = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(4, new PrefixedThreadFactory(MercantorClientConstants.WORKER_THREAD_PREFIX)));
        Injector injector = Guice.createInjector(new MercantorClientModule(mercantorClientConfig));
        injector.injectMembers(this);
    }

    @Override
    public ListenableFuture<IService> registerService(final String basePath, final String role) {
        Preconditions.checkNotNull(basePath, "basePath cannot be null.");
        Preconditions.checkNotNull(role, "role cannot be null.");

        ListenableFuture<IService> serviceFuture = executorService.submit(() -> serviceManager.registerService(basePath, role));

        registerRegisteringCallback(serviceFuture);
        return serviceFuture;
    }

    /**
     * Register the default callback for the logging of registering a service.
     *
     * @param serviceFuture The future of the registration.
     */
    private void registerRegisteringCallback(ListenableFuture<IService> serviceFuture) {
        Futures.addCallback(serviceFuture, new FutureCallback<IService>() {
            public void onSuccess(@Nullable IService service) {
                logger.info("Registered service with basePath {} and role {}. The service got the id {}.", service.getBasePath(), service.getRole(), service.getServiceKey());
            }

            public void onFailure(Throwable throwable) {
                logger.error("Error while registering service: '{}'. ", throwable.getMessage());
            }
        });
    }

    @Override
    public ListenableFuture<Boolean> unregisterService(final IService service) {
        Preconditions.checkNotNull(service, "service cannot be null.");

        ListenableFuture<Boolean> serviceFuture = executorService.submit(() -> serviceManager.removeService(service));

        registerUnregisteringCallback(serviceFuture, service);
        return serviceFuture;
    }

    @Override
    public ListenableFuture<IService> getService(final String role) {
        Preconditions.checkNotNull(role, "tolr cannot be null.");

        final ListenableFuture<IService> serviceFuture = executorService.submit(() -> serviceManager.getService(role));

        registerFetchingCallback(serviceFuture, role);
        return serviceFuture;
    }

    /**
     * Register the default callback for the logging of the fetching of a service.
     *
     * @param serviceFuture The future of the fetching.
     */
    private void registerFetchingCallback(ListenableFuture<IService> serviceFuture, final String role) {
        Futures.addCallback(serviceFuture, new FutureCallback<IService>() {
            @Override
            public void onSuccess(@Nullable IService service) {
                if (service == null) {
                    logger.info("Failed fetching a service for role {}", role);
                    return;
                }
                logger.info("Fetched service at {} with role {} identified by {}", service.getBasePath(), service.getRole(), service.getServiceKey());
            }

            @Override
            public void onFailure(Throwable throwable) {
                logger.info("Failed fetching a service for role {}:{}", role, throwable.getMessage());
            }
        });
    }

    /**
     * Register the default callback for the logging of the removal of a service.
     *
     * @param serviceFuture The future of the removal.
     */
    private void registerUnregisteringCallback(ListenableFuture<Boolean> serviceFuture, final IService service) {
        Futures.addCallback(serviceFuture, new FutureCallback<Boolean>() {
            public void onSuccess(@Nullable Boolean success) {
                logger.info("Registered service with basePath {} and role {}. The service got the id {}.", service.getBasePath(), service.getRole(), service.getServiceKey());
            }

            public void onFailure(Throwable throwable) {
                logger.error("Error while unregistering service {} at {} of role {}: '{}'.", service.getServiceKey(), service.getBasePath(), service.getRole(), throwable.getMessage());
            }
        });
    }
}
