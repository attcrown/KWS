package com.otc.kws.core.domain.repository.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EducationDegree;

@Repository
public interface JpaEducationDegreeRepository extends KwsJpaRepository<EducationDegree, String>
{
	public List<EducationDegree> findByStatus(MasterStatusValue status);
	
	public default List<EducationDegree> findAllActive()
	{
		return findByStatus(MasterStatusValue.Active);
	}
	
	@Query("SELECT e.id, e.educationLevelId, e.schoolTypeId, e.abbrName, e.name, e.status FROM EducationDegree e WHERE e.status = :status")
	public List<Object[]> findFlyweightByStatus(@Param("status") MasterStatusValue status);
	
	public default List<Object[]> findFlyweightAllActive()
	{
		return findFlyweightByStatus(MasterStatusValue.Active);
	}
	
	public default List<EducationDegree> fetchFlyweightAllActive()
	{
		List<Object[]> datas = findFlyweightAllActive();
		
		if(datas != null && !datas.isEmpty()) {
			return datas.stream().map(data -> {
				EducationDegree educationDegree = new EducationDegree();
				educationDegree.setId((String) data[0]);
				educationDegree.setEducationLevelId((String) data[1]);
				educationDegree.setSchoolTypeId((String) data[2]);
				educationDegree.setAbbrName((String) data[3]);
				educationDegree.setName((String) data[4]);
				educationDegree.setStatus((MasterStatusValue) data[5]);
				return educationDegree;
			}).collect(Collectors.toList());
		}
		
		return null;
	}
}