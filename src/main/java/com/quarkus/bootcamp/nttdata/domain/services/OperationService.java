package com.quarkus.bootcamp.nttdata.domain.services;

import java.time.LocalDate;

import com.quarkus.bootcamp.nttdata.domain.entity.AccountWallet;
import com.quarkus.bootcamp.nttdata.domain.repository.AccountWalletRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;


import com.quarkus.bootcamp.nttdata.domain.entity.Operation;

import com.quarkus.bootcamp.nttdata.domain.repository.OperationRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.resource.IPromotionApi;
import com.quarkus.bootcamp.nttdata.infraestructure.request.OperationRequest;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.promotion.Promotion;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class OperationService {

	@Inject
	OperationRepository operationRepository;

	@Inject
	AccountWalletRepository accountRepository;
	
	
	@RestClient
	IPromotionApi iPromotionApi;
	

	public Uni<OperationRequest> validateDatos(OperationRequest operationRequest) {
		Uni<AccountWallet> accsource = accountRepository.findById(operationRequest.getIdAccountSource());
		Uni<AccountWallet> accDestination = accountRepository.findById(operationRequest.getIdAccountDestination());
		
		Uni<Promotion> promotionS = iPromotionApi.getByCode(operationRequest.getPageCode());
		
	return	Uni.combine()
			.all()
			.unis(accsource,accDestination,promotionS)
			.combinedWith(list->{
				AccountWallet cuenta1 =   (AccountWallet)list.get(0);
				AccountWallet cuenta2 =   (AccountWallet)list.get(1);
				Promotion promotion = (Promotion)list.get(2);
				boolean isPayment = !"default".equals(operationRequest.getPageCode());
				
				if(cuenta1 == null
						||  (cuenta2 == null && !isPayment) ){
					throw new  NotFoundException("las cuentas no existen");
				} else if ( isPayment &&  (promotion== null || promotion.getKey() == null )){
					throw new  NotFoundException("EL CODIGO DE PAGO NO EXISTE");
				} else{

					if(cuenta1!=null){
						operationRequest.setAcSource(cuenta1);
					}

					if(cuenta2!=null){
						operationRequest.setAcDestination(cuenta2);
					}
					
					if(isPayment) {
						operationRequest.setDescription(promotion.getTitulo());
						operationRequest.setAmount(promotion.getMonto());
					}

					return operationRequest;
				}



			});
	}


	public Uni<Operation> proccessoperation(OperationRequest operationRequest) {
		Uni<OperationRequest> resultaValidate = validateDatos(operationRequest);
		return resultaValidate.flatMap(e->{
			
			if(!"default".equals(operationRequest.getPageCode())) {
				return proccesPayment(e);
			} else {
				return proccesTransfer(e);
			}
			

		});
	}




	private Uni<Operation> proccesPayment(OperationRequest operRequest){
		Operation operation = new Operation();
		operation.setAmount(operRequest.getAmount());
		operation.setDescription(operRequest.getDescription());
		operation.setFecha(LocalDate.now());
		operation.setIdAccount(operRequest.getAcSource().getId());
		return operationRepository.persist(operation);
	}

	private Uni<Operation> proccesTransfer(OperationRequest operRequest){
		Operation operationS = new Operation();
		operationS.setAmount(operRequest.getAmount()*-1);
		operationS.setDescription(operRequest.getAcDestination().getCellPhoneNumber());
		operationS.setFecha(LocalDate.now());
		operationS.setIdAccount(operRequest.getAcSource().getId());
		return operationRepository.persist(operationS).flatMap(ep->{
					Operation operationD = new Operation();
					operationD.setAmount(operRequest.getAmount());
					operationD.setDescription(operRequest.getAcSource().getCellPhoneNumber());
					operationD.setFecha(LocalDate.now());
			          operationD.setIdAccount(operRequest.getAcDestination().getId());
					return operationRepository.persist(operationD);
				});

	}


}
