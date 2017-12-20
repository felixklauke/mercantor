import de.d3adspace.mercantor.client.MercantorDiscoveryClient;
import de.d3adspace.mercantor.client.MercantorDiscoveryClientFactory;
import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig;

import java.util.HashMap;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class Test {

    public static void main(String[] args) {
        MercantorDiscoveryClient mercantorDiscoveryClient = MercantorDiscoveryClientFactory.INSTANCE
                .createDiscoveryClient(new MercantorDiscoveryClientConfig("http://localhost:8080"));

        mercantorDiscoveryClient.registerService("de.felix_klauke.service.cool",
                "IchBinEinCoolerService", "service.de", "0.0.0.0", 8080, new HashMap<>());

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mercantorDiscoveryClient.discoverService("de.felix_klauke.service.cool").subscribe(
                discoveryResult -> System.out.println("Discovered " + discoveryResult)
        );
    }
}
