package com.quarkus.bootcamp.nttdata.resources;

import com.quarkus.bootcamp.nttdata.entity.accountWallet.AccountWallet;
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
	private String pageCode;
	private String  description;
	private LocalDate fecha;

	private AccountWallet acSource;
	private AccountWallet acDestination;

}
