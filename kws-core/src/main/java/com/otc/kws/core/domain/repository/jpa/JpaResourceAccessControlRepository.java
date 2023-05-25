package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.ResourceAccessControl;

@Repository
public interface JpaResourceAccessControlRepository extends KwsJpaRepository<ResourceAccessControl, String>
{
	public ResourceAccessControl findByAccessToken(String accessToken);
	public List<ResourceAccessControl> findByStatus(MasterStatusValue status);
	
	public default List<ResourceAccessControl> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
}