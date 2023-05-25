package com.otc.kws.core.domain.repository.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EducationMajor;

@Repository
public interface JpaEducationMajorRepository extends KwsJpaRepository<EducationMajor, String>
{
	public List<EducationMajor> findByStatus(MasterStatusValue status);
	
	public default List<EducationMajor> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
	
	public Page<EducationMajor> findByNameContains(String name, Pageable pageable);
	public Page<EducationMajor> findByEducationFacultyIdIn(List<String> educationFacultyIds, Pageable pageable);
	public Page<EducationMajor> findByNameContainsAndEducationFacultyIdIn(String name, List<String> educationFacultyIds, Pageable pageable);
	
	@Query("SELECT e.id, e.schoolTypeId, e.educationFacultyId, e.name, e.status FROM EducationMajor e WHERE e.status = :status")
	public List<Object[]> findFlyweightByStatus(@Param("status") MasterStatusValue status);
	
	@Query("SELECT e.id, e.schoolTypeId, e.educationFacultyId, e.name, e.status FROM EducationMajor e WHERE e.id IN :ids")
	public List<Object[]> findFlyweightByIds(@Param("ids") List<String> ids);
	
	public default List<Object[]> findFlyweightAllActive()
	{
		return findFlyweightByStatus(MasterStatusValue.Active);
	}
	
	public default List<EducationMajor> fetchFlyweightAllActive()
	{
		return transformToFlyweight(findFlyweightAllActive());
	}
	
	public default List<EducationMajor> fetchFlyweightByIds(List<String> ids)
	{
		return transformToFlyweight(findFlyweightByIds(ids));
	}
	
	
	public default List<EducationMajor> transformToFlyweight(List<Object[]> datas)
	{
		if(datas != null && !datas.isEmpty()) {
			return datas.stream().map(data -> {
				EducationMajor educationMajor = new EducationMajor();
				educationMajor.setId((String) data[0]);
				educationMajor.setSchoolTypeId((String) data[1]);
				educationMajor.setEducationFacultyId((String) data[2]);
				educationMajor.setName((String) data[3]);
				educationMajor.setStatus((MasterStatusValue) data[4]);
				return educationMajor;
			}).collect(Collectors.toList());
		}
		return null;
	}
}