package com.quarkus.bootcamp.nttdata.domain.resources;


import com.quarkus.bootcamp.nttdata.domain.services.OperationService;

import com.quarkus.bootcamp.nttdata.infraestructure.request.OperationRequest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/operation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperationResource {

	@Inject
	OperationService operationService;
	
	/**
	 * API PARA AGREGAR UNA OPERACION EN LA BILLETERA ELECTRONICA
	 * 
	 * */
	@POST
	public Uni<Response> add(OperationRequest operation){
		return operationService.proccessoperation(operation)
				.onItem().transform(ac->{
					return Response.ok(ac).status(200).build();
				});
	}
}
