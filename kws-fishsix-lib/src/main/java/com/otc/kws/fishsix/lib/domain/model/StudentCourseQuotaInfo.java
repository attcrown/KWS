package com.otc.kws.fishsix.lib.domain.model;

import java.util.List;

import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseQuotaInfo 
{
	protected StudentCourseQuota studentCourseQuota;
	protected List<String> subjectedIds;
	protected int totalHour;
	protected int studiedHour;
	protected int reservedHour;
	protected int leavedHour;
	protected int availableHour;
}