package com.otc.kws.core.gateway.web.api.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.model.MenuModel;
import com.otc.kws.core.domain.service.menu.FetchMenuCommand;
import com.otc.kws.core.domain.service.menu.MenuService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/menu")
public class FetchMyMenuAPI extends BaseKwsAPI
{
	@Autowired
	protected MenuService menuService;
	
	
	@PostMapping("/fetch-my-menu")
	public ResponseEntity<?> fetchMyMenu(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### FetchMyMenuAPI.fetchMyMenu ###");
		logger.info("requestMessage => " + requestMessage);
		
		FetchMenuCommand.Response fetchMenuResponse = menuService.fetchMenu(req -> {
			setupServiceRequest(request, req);
			req.setPlatform(getAuthSession(request).getPlatform());
			req.setUserProfile(getUserProfile(request));
			req.setGrantedAuthority(getAllGrantedAuthority(request));
			req.setVerifyPrivilege(true);
			req.setActiveProgramId(requestMessage.getActiveProgramId());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setMenus(fetchMenuResponse.getMenuModels());
		bodyResponseMessage.setActiveMenuHierachies(fetchMenuResponse.getActiveMenuHierachies());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String activeProgramId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<MenuModel> menus;
		protected List<MenuModel> activeMenuHierachies;
	}
}