package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.EmailTemplate;

@Repository
public interface JpaEmailTemplateRepository extends KwsJpaRepository<EmailTemplate, String>
{

}