package com.otc.kws.core.domain.dto;

import java.util.List;

import com.otc.kws.core.domain.entity.School;

import lombok.Data;

@Data
public class SchoolSearchResponseDto 
{
	protected int pageNo;
	protected int pageSize;
	protected int totalPage;
	protected int totalSize;
	protected List<School> datas;
}