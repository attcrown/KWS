package com.otc.kws.marketpet.lib.gateway.web.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Address;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.address.AddressService;
import com.otc.kws.marketpet.lib.domain.entity.MkpPet;
import com.otc.kws.marketpet.lib.domain.entity.MkpPetAttribute;
import com.otc.kws.marketpet.lib.domain.service.pet.MkpPetAttributeService;
import com.otc.kws.marketpet.lib.domain.service.pet.MkpPetService;

@RestController
@RequestMapping("/api/marketpet/test/txn")
public class MkpTestTxnAPI 
{
	@Autowired
	protected MkpPetService petService;
	
	@Autowired
	protected MkpPetAttributeService petAttributeService;
	
	@Autowired
	protected AddressService addressService;
	
	
	@GetMapping
	@Transactional
	public Object testTxn(@RequestParam(required = false, defaultValue = "false") boolean error)
	{
		System.out.println("### MkpTestTxnAPI.testTxn ###");
		System.out.println("error => " + error);
		
		MkpPet pet = new MkpPet();
		pet.setId(UUID.randomUUID().toString());
		try {
			pet = petService.create(pet);
			System.out.println("create pet => " + pet);
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		Address address = new Address();
		address.setId(UUID.randomUUID().toString());
		try {
			address = addressService.create(address);
			System.out.println("create address ["+address+"]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		if(error) {
			throw new KwsRuntimeException("Force ERROR for test");
		}
		
		MkpPetAttribute petAttribute = new MkpPetAttribute();
		petAttribute.setId(UUID.randomUUID().toString());
		try {
			petAttribute = petAttributeService.create(petAttribute);
			System.out.println("create petAttribute => " + petAttribute);
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		return "finish";
	}
}