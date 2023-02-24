package com.quarkus.bootcamp.nttdata.repository;

import com.quarkus.bootcamp.nttdata.entity.accountWallet.Operation;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class OperationRepository implements ReactivePanacheMongoRepository<Operation> {

}
