package com.quarkus.bootcamp.nttdata.infraestructure.request;

import com.quarkus.bootcamp.nttdata.domain.entity.AccountWallet;
import org.bson.types.ObjectId;


import lombok.Data;

import java.time.LocalDate;

@Data
public class OperationRequest {
	
	// cuenta de origen aw
	private ObjectId idAccountSource;
	// cuenta de destino aw
	private ObjectId idAccountDestination;
	// monto a pagar, tranferir
	private double amount;
	//  codigo de promocion
	private String pageCode = "default";
	private String  description;
	private LocalDate fecha;

	private AccountWallet acSource;
	private AccountWallet acDestination;

}
