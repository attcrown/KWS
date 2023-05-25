package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Label;

@Repository
public interface JpaLabelRepository extends KwsJpaRepository<Label, Label.CompositeId>
{

}