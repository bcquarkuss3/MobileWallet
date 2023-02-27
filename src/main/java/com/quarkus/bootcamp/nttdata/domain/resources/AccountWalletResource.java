package com.quarkus.bootcamp.nttdata.domain.resources;

import com.quarkus.bootcamp.nttdata.domain.entity.AccountWallet;
import com.quarkus.bootcamp.nttdata.domain.services.AccountWalletService;
import com.quarkus.bootcamp.nttdata.infraestructure.request.AccountWalletRequest;
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
public class AccountWalletResource {
	
	
	@Inject
	AccountWalletService accountService;
	
	
	@POST
	public Uni<Response> add(AccountWalletRequest request){
		return accountService.save(request)
				.onItem().transform(ac->{
					return Response.ok(ac).status(201).build();
				});
	}
}
