package com.otc.kws.core.domain.repository.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EducationFaculty;

@Repository
public interface JpaEducationFacultyRepository extends KwsJpaRepository<EducationFaculty, String>
{
	public List<EducationFaculty> findByStatus(MasterStatusValue status);
	
	default public List<EducationFaculty> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
	
	public Page<EducationFaculty> findByNameContains(String name, Pageable pageable);
	
	@Query("SELECT e.id, e.schoolTypeId, e.name, e.status FROM EducationFaculty e WHERE e.status = :status")
	public List<Object[]> findFlyweightByStatus(@Param("status") MasterStatusValue status);
	
	@Query("SELECT e.id, e.schoolTypeId, e.name, e.status FROM EducationFaculty e WHERE e.id IN :ids")
	public List<Object[]> findFlyweightByIds(@Param("ids") List<String> ids);
	
	public default List<Object[]> findFlyweightAllActive()
	{
		return findFlyweightByStatus(MasterStatusValue.Active);
	}
	
	public default List<EducationFaculty> fetchFlyweightAllActive()
	{
		return transformToFlyweight(findFlyweightAllActive());
	}
	
	public default List<EducationFaculty> fetchFlyweightByIds(List<String> ids)
	{
		return transformToFlyweight(findFlyweightByIds(ids));
	}
	
	
	public default List<EducationFaculty> transformToFlyweight(List<Object[]> datas)
	{
		if(datas != null && !datas.isEmpty()) {
			return datas.stream().map(data -> {
				EducationFaculty educationFaculty = new EducationFaculty();
				educationFaculty.setId((String) data[0]);
				educationFaculty.setSchoolTypeId((String) data[1]);
				educationFaculty.setName((String) data[2]);
				educationFaculty.setStatus((MasterStatusValue) data[3]);
				return educationFaculty;
			}).collect(Collectors.toList());
		}
		return null;
	}
}