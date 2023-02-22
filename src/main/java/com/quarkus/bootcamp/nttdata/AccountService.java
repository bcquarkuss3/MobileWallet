package com.quarkus.bootcamp.nttdata;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AccountService {
	
	@Inject
	AccountRepository accountRepository;
	
	
	public Uni<Account> save(Account account){
		return accountRepository.persist(account);
	}
	
}
