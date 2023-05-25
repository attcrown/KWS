package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterExperience;

@Repository
public interface JpaFjbInternshipRegisterExperienceRepository extends KwsFirstJobberJpaRepository<FjbInternshipRegisterExperience, String>
{
	public List<FjbInternshipRegisterExperience> findByInternshipRegisterId(String internshipRegisterId);
	public List<FjbInternshipRegisterExperience> findByInternshipRegisterIdIn(Collection<String> internshipRegisterIds);
	
	public default List<FjbInternshipRegisterExperience> findByInternshipRegisterIds(Collection<String> internshipRegisterIds)
	{
		return findByInternshipRegisterIdIn(internshipRegisterIds);
	}
}