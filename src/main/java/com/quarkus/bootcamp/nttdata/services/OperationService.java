package com.quarkus.bootcamp.nttdata.services;

import java.time.LocalDate;
import java.util.Optional;

import com.quarkus.bootcamp.nttdata.entity.accountWallet.AccountWallet;
import com.quarkus.bootcamp.nttdata.entity.accountWallet.Operation;
import com.quarkus.bootcamp.nttdata.repository.AccountRepository;
import com.quarkus.bootcamp.nttdata.repository.OperationRepository;
import com.quarkus.bootcamp.nttdata.resources.OperationRequest;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class OperationService {

	@Inject
	OperationRepository operationRepository;

	@Inject
	AccountRepository accountRepository;

	public Uni<OperationRequest> validateDatos(OperationRequest operationRequest) {
		Uni<AccountWallet> accsource = accountRepository.findById(operationRequest.getIdAccountSource());
		Uni<AccountWallet> accDestination = accountRepository.findById(operationRequest.getIdAccountDestination());
		
	return	Uni.combine()
			.all()
			.unis(accsource,accDestination)
			.combinedWith(list->{
				AccountWallet cuenta1 =   (AccountWallet)list.get(0);
				AccountWallet cuenta2 =   (AccountWallet)list.get(1);
				if(cuenta1 == null
						||  (cuenta2 == null && operationRequest.getPageCode() !=null)){
					throw new  NotFoundException("las cuentas no existen");
				} else{

					if(cuenta1!=null){
						operationRequest.setAcSource(cuenta1);
					}

					if(cuenta2!=null){
						operationRequest.setAcDestination(cuenta2);
					}

					return operationRequest;
				}



			});
	}


	public Uni<Operation> proccessoperation(OperationRequest operationRequest) {
		Uni<OperationRequest> resultaValidate = validateDatos(operationRequest);
		return resultaValidate.flatMap(e->{

			return proccesTransfer(e);

		});
	}




	private Uni<Operation> proccesPayment(OperationRequest operRequest){
		Operation operation = new Operation();
		operation.setAmount(operRequest.getAmount());
		operation.setDescription(operRequest.getDescription());
		operation.setFecha(LocalDate.now());
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
