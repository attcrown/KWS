package com.otc.kws.core.domain.dto;

import java.util.List;

import com.otc.kws.core.domain.entity.EducationMajor;

import lombok.Data;

@Data
public class EducationMajorSearchResponseDto 
{
	protected int pageNo;
	protected int pageSize;
	protected int totalPage;
	protected int totalSize;
	protected List<EducationMajor> datas;
}