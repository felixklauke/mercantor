package de.d3adspace.mercantor.client;

import com.google.common.util.concurrent.ListenableFuture;
import de.d3adspace.mercantor.shared.transport.IService;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IMercantorClient {

    ListenableFuture<IService> registerService(String basePath, String role);

    ListenableFuture<Boolean> unregisterService(IService service);

    ListenableFuture<IService> getService(String role);
}
