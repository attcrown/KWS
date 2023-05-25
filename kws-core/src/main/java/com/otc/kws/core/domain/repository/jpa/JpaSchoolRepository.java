package com.otc.kws.core.domain.repository.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.SchoolOwnerTypeValue;
import com.otc.kws.core.domain.entity.School;

@Repository
public interface JpaSchoolRepository extends KwsJpaRepository<School, String>
{
	public List<School> findByStatus(MasterStatusValue status);
	
	public default List<School> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
	
	public Page<School> findByNameContains(String name, Pageable pageable);
	public Page<School> findBySchoolTypeIdIn(List<String> schoolTypeIds, Pageable pageable);
	public Page<School> findBySchoolOwnerTypeIdIn(List<SchoolOwnerTypeValue> schoolOwnerTypeIds, Pageable pageable);
	public Page<School> findByNameContainsAndSchoolTypeIdIn(String name, List<String> schoolTypeIds, Pageable pageable);
	public Page<School> findByNameContainsAndSchoolOwnerTypeIdIn(String name, List<SchoolOwnerTypeValue> schoolOwnerTypeIds, Pageable pageable);
	public Page<School> findByNameContainsAndSchoolTypeIdInAndSchoolOwnerTypeIdIn(String name, List<String> schoolTypeIds, List<SchoolOwnerTypeValue> schoolOwnerTypeIds, Pageable pageable);
	public Page<School> findBySchoolTypeIdInAndSchoolOwnerTypeIdIn(List<String> schoolTypeIds, List<SchoolOwnerTypeValue> schoolOwnerTypeIds, Pageable pageable);
	
	@Query("SELECT e.id, e.schoolTypeId, e.schoolOwnerTypeId, e.name, e.status FROM School e WHERE e.status = :status")
	public List<Object[]> findFlyweightByStatus(@Param("status") MasterStatusValue status);
	
	@Query("SELECT e.id, e.schoolTypeId, e.schoolOwnerTypeId, e.name, e.status FROM School e WHERE e.id IN :ids")
	public List<Object[]> findFlyweightByIds(@Param("ids") List<String> ids);
	
	public default List<Object[]> findFlyweightAllActive()
	{
		return findFlyweightByStatus(MasterStatusValue.Active);
	}
	
	public default List<School> fetchFlyweightAllActive()
	{
		return transformToFlyweight(findFlyweightAllActive());
	}
	
	public default List<School> fetchFlyweightByIds(List<String> ids)
	{
		return transformToFlyweight(findFlyweightByIds(ids));
	}
	
	public default List<School> transformToFlyweight(List<Object[]> datas)
	{
		if(datas != null && !datas.isEmpty()) {
			return datas.stream().map(data -> {
				School school = new School();
				school.setId((String) data[0]);
				school.setSchoolTypeId((String) data[1]);
				school.setSchoolOwnerTypeId((SchoolOwnerTypeValue) data[2]);
				school.setName((String) data[3]);
				school.setStatus((MasterStatusValue) data[4]);
				return school;
			}).collect(Collectors.toList());
		}
		return null;
	}
}