package com.quarkus.bootcamp.nttdata.infraestructure.resource;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.card.CardD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient
@Path("/cards")
public interface ICardApi {
	
	@GET
	Uni<List<CardD>> getAll(@QueryParam("customerId") Long customerId, @QueryParam("cardTypeId") Long cardTypeId);


	@GET()
	@Path("/{serial}")
	public Uni<CardD> findCardsBySerial(@QueryParam("serial") String serial);

}
