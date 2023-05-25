package com.otc.kws.firstjobber.lib.domain.dto;

import java.util.List;

import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;

import lombok.Data;

@Data
public class FjbEmployerSearchResponseDto 
{
	protected int pageNo;
	protected int pageSize;
	protected int totalPage;
	protected int totalSize;
	protected List<FjbEmployer> datas;
}