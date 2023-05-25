package com.otc.kws.core.gateway.web.controller.workspace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.otc.kws.core.domain.entity.Menu;
import com.otc.kws.core.domain.model.MenuModel;
import com.otc.kws.core.domain.service.menu.FetchMenuCommand;
import com.otc.kws.core.domain.service.menu.MenuService;
import com.otc.kws.core.gateway.web.controller.BaseController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public abstract class WorkspaceController extends BaseController
{
	public static final String PREFIX_PATH = "/views/workspace";
	
	@Autowired
	protected MenuService menuService;
	
	
	@GetMapping
	public String handle(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
        initial(request, response, model);
        return getRuntimePrefixViewFilePath(request) + "/workspace";
	}
	
	protected void initial(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		model.addAttribute("kws_webEnv", getWebEnv(request));
		model.addAttribute("kws_databaseEnv", getDatabaseEnv(request));
		model.addAttribute("kws_contextPath", request.getContextPath());
    	model.addAttribute("kws_contextPath", request.getContextPath());
    	model.addAttribute("kws_requestURI", request.getRequestURI());
    	model.addAttribute("kws_webTemplate", getCurrentWebTemplate(request));
    	model.addAttribute("kws_fragmentNavbarFile", getFragmentNavbarFile(request));
    	model.addAttribute("kws_fragmentSidebarFile", getFragmentSidebarFile(request));
    	model.addAttribute("kws_fragmentFooterFile", getFragmentFooterFile(request));
    	model.addAttribute("kws_fragmentWorkspaceHeaderFile", getFragmentWorkspaceHeaderFile(request));
    	model.addAttribute("kws_viewFile", getViewFile(request));
    	model.addAttribute("kws_userProfile", getUserProfile(request));
    	model.addAttribute("kws_currentGrantedAuthority", getCurrentGrantedAuthority(request));
    	model.addAttribute("kws_currentLabelLangId", getCurrentLabelLanguageId(request));
    	model.addAttribute("kws_workspaceLabels", buildWorkspaceLabels(request));
    	model.addAttribute("kws_menus", buildMyMenus(request));
    	model.addAttribute("kws_pageTitle", getPageTitle(request));
    	model.addAttribute("kws_activeMenus", getCurrentActiveMenuHierachies(request));
    	model.addAttribute("kws_breadcrumbs", buildBreadcrumbs(request));
		
		setupModel(request, response, model);
	}
	
	@Override
	protected String getPrefixViewFilePath(HttpServletRequest request)
	{
		return getCurrentWebTemplate(request) + "/" + getModuleName(request);
	}
	
	protected abstract void setupModel(HttpServletRequest request, HttpServletResponse response, Model model);
	
	protected String getFragmentNavbarFile(HttpServletRequest request)
	{
		return configService.getWebFragmentNavbarFile(getRuntimeModuleName(request));
	}
	
	protected String getFragmentSidebarFile(HttpServletRequest request)
	{
		return configService.getWebFragmentSidebarFile(getRuntimeModuleName(request));
	}
	
	protected String getFragmentFooterFile(HttpServletRequest request)
	{
		return configService.getWebFragmentFooterFile(getRuntimeModuleName(request));
	}
	
	protected String getFragmentWorkspaceHeaderFile(HttpServletRequest request)
	{
		return configService.getWebFragmentWorkspaceHeaderFile(getRuntimeModuleName(request));
	}
	
	protected String getViewFile(HttpServletRequest request)
	{
		return getCurrentWeb(request).getViewFile().replace("${web_template}", getCurrentWebTemplate(request));
	}
	
	protected String getPageTitle(HttpServletRequest request)
	{
		return getCurrentWeb(request).getPageTitle();
	}
	
	protected String getPageDescription(HttpServletRequest request)
	{
		return getCurrentWeb(request).getPageDescription();
	}
	
	protected List<MenuModel> buildMyMenus(HttpServletRequest request)
	{
		List<MenuModel> menuModels = null;
		
		if(request.getAttribute(MenuModel.class.getName()) != null) {
			menuModels = (List<MenuModel>) request.getAttribute(MenuModel.class.getName());
		}
		
		if(menuModels == null) {
			FetchMenuCommand.Response response = menuService.fetchMenu(req -> {
				setupServiceRequest(request, req);
				req.setPlatform(getAuthSession(request).getPlatform());
				req.setUserProfile(getUserProfile(request));
				req.setGrantedAuthority(getAllGrantedAuthority(request));
				req.setVerifyPrivilege(true);
				req.setActiveProgramId(getCurrentWeb(request).getId());
			});
			
			menuModels = response.getMenuModels();
			request.setAttribute("activeMenuHierachies", response.getActiveMenuHierachies());
		}
		
		return menuModels;
	}
	
	/*
	protected List<Menu> buildActiveMenus(HttpServletRequest request) throws JsonMappingException, JsonProcessingException
	{
		if(getCurrentWeb(request).getActiveMenus() != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(getCurrentWeb(request).getActiveMenus(), MenuList.class);
		}
		return null;
	}
	
	protected List<Breadcrumb> buildBreadcrumbs(HttpServletRequest request) throws JsonMappingException, JsonProcessingException
	{
		if(getCurrentWeb(request).getBreadcrumbs() != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(getCurrentWeb(request).getBreadcrumbs(), BreadcrumbList.class);
		}
		return null;
	}
	*/
	
	protected List<Label> buildWorkspaceLabels(HttpServletRequest request)
	{
		return null;
	}
	
	protected List<MenuModel> getCurrentActiveMenuHierachies(HttpServletRequest request)
	{
		return (List<MenuModel>) request.getAttribute("activeMenuHierachies");
	}
	
	protected List<Breadcrumb> buildBreadcrumbs(HttpServletRequest request) 
    {
    	List<MenuModel> activeMenuHierachies = getCurrentActiveMenuHierachies(request);
    	
    	if(activeMenuHierachies != null && !activeMenuHierachies.isEmpty()) {
    		return activeMenuHierachies.stream().
    				map(menuModel -> {
    					String icon = "";
	    				if(menuModel.getIconType() == Menu.IconType.AwesomeFont) {
	    					icon = menuModel.getIconFontClass();
	    				} else {
	    					icon = menuModel.getIconImageURI();
	    				}
	    				
		    			if(menuModel.getItemType() == Menu.ItemType.MenuItem) {
		    				return new Breadcrumb(menuModel.getTitle(), icon, menuModel.getHref(), menuModel.isActive(), true);
		    			} else {
		    				return new Breadcrumb(menuModel.getTitle(), icon, menuModel.getHref(), menuModel.isActive(), false);
		    			}
		    		}).
    				collect(Collectors.toList());
    	} else {
    		return Arrays.asList(
				new Breadcrumb(getPageTitle(request), "fa fa-circle", "", true, true)
			);
    	}
	}
	
	
	@Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Label
    {
    	protected String code;
    	protected List<Value> values;
    	
    	
    	@Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Value
        {
    		protected String languageId;
    		protected String value;
        }
    }
	
	
	@Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Breadcrumb
    {
    	protected String title;
    	protected String icon;
    	protected String href;
    	protected boolean active;
    	protected boolean leaf;
    }
	
	
	public static class BreadcrumbList extends ArrayList<Breadcrumb>
	{
		
	}
}