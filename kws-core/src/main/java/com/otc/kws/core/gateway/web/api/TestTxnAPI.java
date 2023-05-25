package com.otc.kws.core.gateway.web.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Address;
import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.address.AddressService;
import com.otc.kws.core.domain.service.filestore.FileStoreLogService;

@RestController
@RequestMapping("/api/core/test/txn")
public class TestTxnAPI 
{
	@Autowired
	protected AddressService addressService;
	
	@Autowired
	protected FileStoreLogService fileStoreLogService;
	
	
	@GetMapping
	@Transactional
	public Object testTxn(@RequestParam(defaultValue = "false") boolean error)
	{
		System.out.println("### TestTxnAPI.testTxn ###");
		System.out.println("error => " + error);
		
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
		
		FileStoreLog fileStoreLog = new FileStoreLog();
		fileStoreLog.setId(UUID.randomUUID().toString());
		try {
			fileStoreLog = fileStoreLogService.create(fileStoreLog);
			System.out.println("create fileStoreLog ["+fileStoreLog+"]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		return "finish";
	}
}