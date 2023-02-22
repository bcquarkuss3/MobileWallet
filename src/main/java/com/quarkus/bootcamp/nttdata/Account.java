package com.quarkus.bootcamp.nttdata;


import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;

@Data
@MongoEntity(collection = "account")
public class Account {
	
	 private ObjectId  id;
	 private String cellphonenumber;
	
}
