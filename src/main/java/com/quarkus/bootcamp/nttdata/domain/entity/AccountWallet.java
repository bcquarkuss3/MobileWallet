package com.quarkus.bootcamp.nttdata.domain.entity;


import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MongoEntity(collection = "account")
public class AccountWallet {


	 private ObjectId  id;


	// private  ObjectId cardId;
	/**
	 * numero de celular
	 */

	 private String cellPhoneNumber;

	/**
	 * numero de tarjeta
	 */
	private  String serial;

	/**
	 * contraseña
	 */
	 private  String password;

	/**
	 * id del cliente
	 */
	 private  String customerId;

	//*******************************
	//*** CAMPOS DE AUDITORIA
	//*******************************
	/**
	 * codigo de usuario de alta
	 */
	private String cdUsuAlta;

	/**
	 * codigo de usuario de modificación
	 */
	private String cdUsuModif;

	/**
	 * codigo de usuario de baja
	 */
	private String cdUsuBaja;

	/**
	 * fecha de alta
	 */
	private LocalDateTime fcAltaFila;

	/**
	 * fecha de modificacion
	 */
	private LocalDateTime fcModifFila;

	/**
	 * fecha de baja
	 */
	private LocalDateTime fcBajaFila;
	
}
