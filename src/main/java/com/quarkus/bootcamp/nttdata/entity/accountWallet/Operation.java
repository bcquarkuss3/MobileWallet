package com.quarkus.bootcamp.nttdata.entity.accountWallet;

import java.time.LocalDate;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;

@Data
@MongoEntity(collection = "operation")
public class Operation {
	
	 private ObjectId  id;
	 private ObjectId  idAccount;
	 private String description;
	 private double amount;
	 private LocalDate fecha;
}
