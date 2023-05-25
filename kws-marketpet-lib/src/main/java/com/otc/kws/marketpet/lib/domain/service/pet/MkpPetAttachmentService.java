package com.otc.kws.marketpet.lib.domain.service.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpPetAttachment;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpPetAttachmentRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpPetAttachmentService extends RepositoryKwsMarketPetService<JpaMkpPetAttachmentRepository, MkpPetAttachment, String>
{
	@Autowired
	protected JpaMkpPetAttachmentRepository mkpPetAttachmentRepository;

	
	@Override
	protected JpaMkpPetAttachmentRepository getRepository() 
	{
		return mkpPetAttachmentRepository;
	}

	@Override
	protected String getId(MkpPetAttachment entity) 
	{
		return entity.getId();
	}
}