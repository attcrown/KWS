package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.NotificationTemplate;

@Repository
public interface JpaNotificationTemplateRepository extends KwsJpaRepository<NotificationTemplate, String>
{

}