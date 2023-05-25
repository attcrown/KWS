package com.otc.kws.core.domain.dto;

import java.util.List;

import com.otc.kws.core.domain.entity.EducationFaculty;

import lombok.Data;

@Data
public class EducationFacultySearchResponseDto 
{
	protected int pageNo;
	protected int pageSize;
	protected int totalPage;
	protected int totalSize;
	protected List<EducationFaculty> datas;
}