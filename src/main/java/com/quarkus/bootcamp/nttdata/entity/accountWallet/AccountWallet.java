package com.quarkus.bootcamp.nttdata.entity.accountWallet;


import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;

@Data
@MongoEntity(collection = "account")
public class AccountWallet {


	 private ObjectId  id;

	 private String cellPhoneNumber;

	 private  String numberCard;

	 private  String password;
	
}
