package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.NotificationConfig;

@Repository
public interface JpaNotificationConfigRepository extends KwsJpaRepository<NotificationConfig, String>
{

}