package de.d3adspace.mercantor.client;

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
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientImpl implements IMercantorClient {

    private final Logger logger;
    private final ListeningScheduledExecutorService executorService;

    @Inject
    private IServiceManager serviceManager;

    MercantorClientImpl(MercantorClientConfig mercantorClientConfig) {
        this.logger = LoggerFactory.getLogger(MercantorClientImpl.class);
        this.executorService = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(4, new PrefixedThreadFactory(MercantorClientConstants.WORKER_THREAD_PREFIX)));
        Injector injector = Guice.createInjector(new MercantorClientModule(mercantorClientConfig));
        injector.injectMembers(this);
    }

    public ListenableFuture<IService> registerService(final String basePath, final String role) {
        ListenableFuture<IService> serviceFuture = executorService.submit(() -> serviceManager.registerService(basePath, role));

        registerRegisteringCallback(serviceFuture);
        return serviceFuture;
    }

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

    public ListenableFuture<Boolean> unregisterService(final IService service) {
        ListenableFuture<Boolean> serviceFuture = executorService.submit(() -> serviceManager.removeService(service));

        registerUnregisteringCallback(serviceFuture, service);
        return serviceFuture;
    }

    @Override
    public ListenableFuture<IService> getService(final String role) {
        final ListenableFuture<IService> serviceFuture = executorService.submit(() -> serviceManager.getService(role));

        registerFetchingCallback(serviceFuture, role);
        return serviceFuture;
    }

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
