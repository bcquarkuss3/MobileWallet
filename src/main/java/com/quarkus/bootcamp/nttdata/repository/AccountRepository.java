package com.quarkus.bootcamp.nttdata.repository;

import com.quarkus.bootcamp.nttdata.entity.accountWallet.AccountWallet;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountRepository implements ReactivePanacheMongoRepository<AccountWallet> {

}
