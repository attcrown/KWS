package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrderStatus;

@Repository
public interface JpaCourseOrderStatusRepository extends KwsFishsixJpaRepository<CourseOrderStatus, String>
{
	public List<CourseOrderStatus> findByStatus(MasterStatusValue status);
	
	public default List<CourseOrderStatus> findAllActive()
	{
		List<CourseOrderStatus> orderStatuses = findByStatus(MasterStatusValue.Active);
		
		if(orderStatuses != null && !orderStatuses.isEmpty()) {
			orderStatuses.sort((o1, o2) -> o1.getSeqNo() - o2.getSeqNo());
		}
		
		return orderStatuses;
	}
}