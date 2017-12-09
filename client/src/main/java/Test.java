import de.d3adspace.mercantor.commons.codec.ServiceClusterMessageBodyReader;
import de.d3adspace.mercantor.commons.codec.ServiceClusterMessageBodyWriter;
import de.d3adspace.mercantor.commons.codec.ServiceMessageBodyReader;
import de.d3adspace.mercantor.commons.codec.ServiceMessageBodyWriter;
import de.d3adspace.mercantor.commons.model.service.ServiceClusterModel;
import de.d3adspace.mercantor.commons.model.service.ServiceModel;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class Test {

    public static void main(String[] args) {
        Client client = ClientBuilder.newBuilder().register(new ServiceMessageBodyReader()).register(new ServiceMessageBodyWriter()).register(new ServiceClusterMessageBodyReader()).register(new ServiceClusterMessageBodyWriter()).build();

        ServiceModel service = new ServiceModel("de.felix_klauke.service.string", "node-01", "gaylord", 1234);

        for (int i = 0; i < 100; i++) {
            client.target("http://127.0.0.1:8080/v1/service/register").request(MediaType.APPLICATION_JSON).post(Entity.json(service));
        }

        try {
            System.out.println(client.target("http://127.0.0.1:8080/v1/service/get/de.felix_klauke.service.string")
                    .queryParam("limit", 5)
                    .request(MediaType.APPLICATION_JSON)
                    .async().get().get()
                    .readEntity(ServiceClusterModel.class));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
