package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.UserGroupMapper;

@Repository
public interface JpaUserGroupMapperRepository extends KwsJpaRepository<UserGroupMapper, UserGroupMapper.CompositeId>
{

}