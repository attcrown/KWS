package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductAttachment;

@Repository
public interface JpaMkpProductAttachmentRepository extends KwsMarketPetJpaRepository<MkpProductAttachment, String>
{

}