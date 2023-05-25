package com.otc.kws.core.domain.repository.jpa;

import com.otc.kws.core.domain.entity.LineNotifyChannel;

import org.springframework.stereotype.Repository;

@Repository
public interface JpaLineNotifyChannelRepository extends KwsJpaRepository<LineNotifyChannel, String>
{
    
}