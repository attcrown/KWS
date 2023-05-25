package com.otc.kws.core.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otc.kws.core.domain.entity.Menu;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuModel 
{
	protected String menuId;
	protected String programId;
	protected Menu.ItemType itemType;
	protected Menu.HierachyType hierachyType;
	protected int hierachyLevel;
	protected int seqNo;
	protected String title;
	protected String description;
	protected Menu.IconType iconType;
	protected String iconImageURI;
	protected String iconFontClass;
	protected String href;
	protected boolean active;
	protected List<MenuModel> children;
}