package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Relationship;

@Repository
public interface JpaRelationshipRepository extends KwsJpaRepository<Relationship, String>
{

}