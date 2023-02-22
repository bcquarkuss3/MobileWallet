package com.quarkus.bootcamp.nttdata;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
	
	
	@Inject
	AccountService accountService;
	
	
	@POST
	public Uni<Response> add(Account account){
		return accountService.save(account)
				.onItem().transform(ac->{
					return Response.ok(ac).status(200).build();
				});
	}
}
