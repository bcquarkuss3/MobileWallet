package com.quarkus.bootcamp.nttdata.services;

import com.quarkus.bootcamp.nttdata.entity.accountWallet.AccountWallet;
import com.quarkus.bootcamp.nttdata.repository.AccountRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AccountService {
	
	@Inject
	AccountRepository accountRepository;
	
	
	public Uni<AccountWallet> save(AccountWallet account){
		return accountRepository.persist(account);
	}
	
}
