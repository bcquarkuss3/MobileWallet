package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.AccountWallet;
import com.quarkus.bootcamp.nttdata.domain.repository.AccountWalletRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.card.CardD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.BodyCorporateD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.NaturalPersonD;
import com.quarkus.bootcamp.nttdata.infraestructure.request.AccountWalletRequest;
import com.quarkus.bootcamp.nttdata.infraestructure.resource.IBodyCorporateApi;
import com.quarkus.bootcamp.nttdata.infraestructure.resource.ICardApi;
import com.quarkus.bootcamp.nttdata.infraestructure.resource.INaturalPersonApi;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class AccountWalletService {

	@Inject
	AccountWalletRepository accountRepository;


	@RestClient
	INaturalPersonApi clientNaturalPerson;

	@RestClient
	ICardApi clientCard;


	public Uni<AccountWallet> save(AccountWalletRequest request) {
		Uni<NaturalPersonD> naturalPerson = clientNaturalPerson.getById(Long.parseLong(request.getCustomerId()));
		return naturalPerson.onItem().transformToUni(pn->{
			if(Objects.isNull(pn)  ){
				throw new NotFoundException("Persona natural o Jurídica no encontrada : " );
			}
			return  saveAccountWallet(request);
		});

	}


	/**
	 * método para acceso login de billetera
	 * @param request
	 * @return
	 */
	public Uni<AccountWallet> acceso(AccountWalletRequest request){

		if(Objects.isNull(request)){

			throw new NotFoundException("Error, datos vacíos");
		}
		Uni<CardD> card =   clientCard.findCardsBySerial(request.getCard().getSerial());

		Uni<List<AccountWallet>> listAccountW =  accountRepository.findAll().list();

		return card.onItem().transformToUni(c->{
			if(Objects.isNull(c) || Objects.isNull(c.getId())) {
				throw new NotFoundException("cards not found");
			

			}

			if(Objects.nonNull(c)) {

			this.callCardsCustomer(request);


				return null;

				//		listAccountW.onItem().<AccountWallet>disjoint()
				//		.filter(account -> (account.getSerial().equals(c.getSerial())
				//				&& account.getCardId().equals(c.getId()))).collect().first();



			}
		//	return null;
			return null;
		});
	}


	private Uni<AccountWallet> findCardsBySerial(String serial) {
		return accountRepository.find("{'serial': ?1}", serial).firstResult();
	}

	/**
	 * método para guardar  accountWallet
	 *
	 * @param request
	 * @return
	 */
	private Uni<AccountWallet> saveAccountWallet(AccountWalletRequest request) {

		AccountWallet accountW = new AccountWallet();


		String contrasenia = request.getPassword();

		if(contrasenia.length()>6){

			throw new NotFoundException("Error, la contraseña no debe ser mayor a 7 caracteres : " + contrasenia);
		}

		LocalDateTime fechaHoy = LocalDateTime.now();
		accountW.setCdUsuAlta("Admin");
		accountW.setFcAltaFila(fechaHoy);
		accountW.setCustomerId(request.getCustomerId()); // id enviado del customer
		accountW.setCellPhoneNumber(request.getCellPhoneNumber());
		accountW.setSerial(request.getCard().getSerial()); // numero de tarjeta
		//accountW.setCardId(new ObjectId(String.valueOf(request.getCard().getId())));
		accountW.setPassword(contrasenia);

		return  accountRepository.persist(accountW);

	}

	/**
	 * mpetodo para validar cards y customers
	 * @param request
	 * @return
	 */

	private Uni<CardD> callCardsCustomer(AccountWalletRequest request) {
		Uni<List<CardD>> usersCards = clientCard.getAll(Long.parseLong(request.getCustomerId()), Long.parseLong("2"));
		return usersCards.onItem().<CardD>disjoint()
				.filter(uc -> (uc.getSerial().equals(request.getCard().getSerial())
						&& uc.getMonth().intValue() == request.getCard().getMonth().intValue()
						&& uc.getYear().intValue() == request.getCard().getYear().intValue()
						&& uc.getCvv() == request.getCard().getCvv()))
				.collect().first();

	}


	public Uni<AccountWallet> login(AccountWallet accountWallet) {
		Uni<AccountWallet> accountWalletUni =   findCardsBySerial(accountWallet.getSerial());
		return accountWalletUni.onItem().transformToUni(aw->{
			if(aw == null || aw.getId()==null) {
				throw new NotFoundException("cards not found");
			} else {
				return accountRepository.findById(aw.getId());

			}
		});
	}


	/**
	 * lista todo los registros.
	 * @return
	 */
	public  Multi<AccountWallet> listar(){
		return accountRepository.streamAll();
	}

}
