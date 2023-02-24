package com.quarkus.bootcamp.nttdata.resources;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient
@Path("/promotion")
public interface IPromotionApi {
	
	@GET
	@Path("/{key}")
	Uni<Promotion> getByCode(@PathParam("key") String key);

}
