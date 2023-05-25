package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.NotificationChannel;

@Repository
public interface JpaNotificationChannelRepository extends KwsJpaRepository<NotificationChannel, String>
{

}