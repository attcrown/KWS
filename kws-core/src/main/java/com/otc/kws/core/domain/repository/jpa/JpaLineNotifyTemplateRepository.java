package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.LineNotifyTemplate;

@Repository
public interface JpaLineNotifyTemplateRepository extends KwsJpaRepository<LineNotifyTemplate, String>
{
	public List<LineNotifyTemplate> findByNotificationConfigIdAndStatus(String notificationConfigId, MasterStatusValue status);
	
	public default List<LineNotifyTemplate> findAllActiveByNotificationConfigId(String notificationConfigId)
	{
		return findByNotificationConfigIdAndStatus(notificationConfigId, MasterStatusValue.Active);
	}
}