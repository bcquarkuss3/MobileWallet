package com.quarkus.bootcamp.nttdata.domain.repository;

import com.quarkus.bootcamp.nttdata.domain.entity.AccountWallet;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountWalletRepository implements ReactivePanacheMongoRepository<AccountWallet> {

}
