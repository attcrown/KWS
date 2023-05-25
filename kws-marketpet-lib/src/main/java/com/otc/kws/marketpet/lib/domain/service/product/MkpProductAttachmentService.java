package com.otc.kws.marketpet.lib.domain.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductAttachment;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpProductAttachmentRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpProductAttachmentService extends RepositoryKwsMarketPetService<JpaMkpProductAttachmentRepository, MkpProductAttachment, String>
{
	@Autowired
	protected JpaMkpProductAttachmentRepository mkpProductAttachmentRepository;

	
	@Override
	protected JpaMkpProductAttachmentRepository getRepository() 
	{
		return mkpProductAttachmentRepository;
	}

	@Override
	protected String getId(MkpProductAttachment entity) 
	{
		return entity.getId();
	}
}