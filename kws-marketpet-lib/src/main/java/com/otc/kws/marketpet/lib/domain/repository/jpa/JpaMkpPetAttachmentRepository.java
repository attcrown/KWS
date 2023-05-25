package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpPetAttachment;

@Repository
public interface JpaMkpPetAttachmentRepository extends KwsMarketPetJpaRepository<MkpPetAttachment, String>
{

}