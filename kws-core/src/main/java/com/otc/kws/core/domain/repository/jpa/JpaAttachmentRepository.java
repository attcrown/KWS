package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Attachment;

@Repository
public interface JpaAttachmentRepository extends KwsJpaRepository<Attachment, String>
{

}