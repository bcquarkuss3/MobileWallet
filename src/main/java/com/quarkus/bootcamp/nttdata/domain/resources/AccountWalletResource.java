package com.quarkus.bootcamp.nttdata.domain.resources;

import com.quarkus.bootcamp.nttdata.domain.entity.AccountWallet;
import com.quarkus.bootcamp.nttdata.domain.services.AccountWalletService;
import com.quarkus.bootcamp.nttdata.infraestructure.request.AccountWalletRequest;
import com.quarkus.bootcamp.nttdata.infraestructure.request.ResponseDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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


	@Path("/login")
	@POST
	public Uni<Response> login(AccountWallet accountWallet) {
		return accountService.login(accountWallet).onItem().transform(us -> {
			if (us.getPassword().equals(accountWallet.getPassword())) {
				return Response.ok(new ResponseDto<>(200, "login successed")).status(200).build();
			} else {
				return Response.ok(new ResponseDto<>(422, "login fault")).status(422).build();
			}
		});
	}




	@GET
	public Multi<Response> listar() throws Exception {

		return this.accountService.listar().onItem().transform(Response::ok)
				.onItem().transform(Response.ResponseBuilder::build);
	}


}
