package com.otc.kws.core.domain.service.menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.PlatformValue;
import com.otc.kws.core.domain.constant.value.ResourceActionValue;
import com.otc.kws.core.domain.entity.Menu;
import com.otc.kws.core.domain.entity.Menu.HierachyType;
import com.otc.kws.core.domain.entity.Menu.ItemType;
import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.model.GrantedAuthority;
import com.otc.kws.core.domain.model.MenuModel;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.repository.jpa.JpaMenuRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.WebService;

import lombok.Data;

@Component
public class FetchMenuCommand extends BaseKwsCommand
{
	protected final Comparator<Menu> sortByHierachyLevelAndSeqNo = Comparator.comparing(Menu::getHierachyLevel).thenComparing(Menu::getSeqNo);
	
	
	@Autowired
	protected JpaMenuRepository menuRepository;
	
	@Autowired
	protected WebService webService;
	
	
	public Response fetchMenu(Request request)
	{
		Response response = new Response();
		
		List<Menu> menus = menuRepository.findAllActiveByPlatform(request.getPlatform());
		
		if(menus == null || menus.isEmpty()) {
			return response;
		}
		
		if(request.isVerifyPrivilege() && request.getGrantedAuthority() != null) {
			List<GrantedAuthority.Privilege> privileges = request.getGrantedAuthority().getPrivileges();
			
			if(privileges == null || privileges.isEmpty()) {
				return response;
			}

			List<String> grantedResourceRefIds = privileges.
					stream().
					filter(privilege -> privilege.getResourceActions().contains(ResourceActionValue.Access.name())).
					map(GrantedAuthority.Privilege::getResourceRefId).
					collect(Collectors.toList());

			if(grantedResourceRefIds == null || grantedResourceRefIds.isEmpty()) {
				return response;
			}
			
			/*
			List<Menu> grantedLeafMenus = menus.
					stream().
					filter(menu -> menu.getHierachyType() == HierachyType.Leaf).
					filter(menu -> !menu.isAuthorize() || (menu.isAuthorize() && grantedResourceRefIds.contains(menu.getProgramId()))).
					collect(Collectors.toList());
			
			if(grantedLeafMenus == null || grantedLeafMenus.isEmpty()) {
				return response;
			}
			*/
			
			/*
			List<Menu> grantedNoneLeafMenus = menus.
					stream().
					filter(menu -> {
						if(menu.getHierachyType() != HierachyType.Leaf) {
							if(! menu.isAuthorize()) {
								return true;
							}
						}
						
						if(menu.getHierachyType() == HierachyType.Root) {
							boolean match = grantedLeafMenus.
									stream().
									filter(leafMenu -> leafMenu.getRootItemId().equals(menu.getId())).
									findAny().
									orElse(null) != null;
							if(match == true) {
								return true;
							}
						} else if(menu.getHierachyType() == HierachyType.Middle) {
							boolean match = grantedLeafMenus.
									stream().
									filter(leafMenu -> leafMenu.getParentItemId().equals(menu.getId())).
									findAny().
									orElse(null) != null;
							if(match == true) {
								return true;
							}
						}
						return false;
					}).
					collect(Collectors.toList());
			*/
			
			List<Menu> grantedMenuItems = menus.
					stream().
					filter(menu -> menu.getItemType() == ItemType.MenuItem).
					filter(menu -> (!menu.isAuthorize()) || (menu.isAuthorize() && grantedResourceRefIds.contains(menu.getProgramId()))).
					collect(Collectors.toList());
			
			List<Menu> grantedNoneMenuItems = menus.
					stream().
					filter(menu -> menu.getItemType() != ItemType.MenuItem).
					filter(menu -> {
						if(! menu.isAuthorize()) {
							return true;
						}
						
						if(menu.getHierachyType() == HierachyType.Root) {
							boolean match = grantedMenuItems.
									stream().
									filter(menuItem -> menuItem.getRootItemId() != null && menuItem.getRootItemId().equals(menu.getId())).
									findAny().
									orElse(null) != null;
							if(match == true) {
								return true;
							}
						} else if(menu.getHierachyType() == HierachyType.Middle) {
							boolean match = grantedMenuItems.
									stream().
									filter(menuItem -> menuItem.getParentItemId() != null && menuItem.getParentItemId().equals(menu.getId())).
									findAny().
									orElse(null) != null;
							if(match == true) {
								return true;
							}
						}
						
						return false;
					}).
					collect(Collectors.toList());

			if(grantedMenuItems == null || grantedMenuItems.isEmpty()) {
				return response;
			}
			
			/*
			menus = grantedLeafMenus;
			
			if(grantedNoneLeafMenus != null && !grantedNoneLeafMenus.isEmpty()) {
				menus.addAll(grantedNoneLeafMenus);
			}
			*/
			
			menus = grantedMenuItems;
			
			if(grantedNoneMenuItems != null && !grantedNoneMenuItems.isEmpty()) {
				menus.addAll(grantedNoneMenuItems);
			}
			
			menus = menus.stream().sorted(sortByHierachyLevelAndSeqNo).collect(Collectors.toList());
		}
		
		List<Menu> rootMenus = menus.
				stream().
				filter(menu -> menu.getHierachyType() == HierachyType.Root).
				sorted((Menu menu1, Menu menu2) -> menu1.getSeqNo() - menu2.getSeqNo()).
				collect(Collectors.toList());
		
		if(rootMenus != null && !rootMenus.isEmpty()) {
			List<MenuModel> menuModels = new ArrayList<MenuModel>();
			MenuModel menuModel = null;
			Menu activedMenu = null;
			
			if(request.getActiveProgramId() != null) {
				/*
				activedMenu = menus.
						stream().
						filter(menu -> menu.getHierachyType() == HierachyType.Leaf).
						filter(menu -> menu.getProgramId().equals(request.getActiveProgramId())).
						findFirst().
						orElse(null);
				*/
				activedMenu = menus.
						stream().
						filter(menu -> menu.getItemType() == ItemType.MenuItem).
						filter(menu -> menu.getProgramId().equals(request.getActiveProgramId())).
						findFirst().
						orElse(null);
			}
			
			for(Menu rootMenu : rootMenus) {
				menuModel = buildMenuModel(rootMenu, activedMenu);
				menuModel.setChildren(findChildren(menus, rootMenu.getId(), activedMenu));
				menuModels.add(menuModel);
			}
			
			response.setMenuModels(menuModels);
			
			if(request.getActiveProgramId() != null) {
				response.setActiveMenuHierachies(activatedMenuAndActiveHierachies(menus, request.getActiveProgramId(), activedMenu));
			}
		}
		
		return response;
	}
	
	
	protected List<MenuModel> findChildren(List<Menu> menus, String parentItemId, Menu activedMenu) 
	{
		List<Menu> childMenus = menus.
				stream().
				filter(menu -> menu.getParentItemId() != null && menu.getParentItemId().equals(parentItemId)).
				sorted((Menu menu1, Menu menu2) -> menu1.getSeqNo() - menu2.getSeqNo()).
				collect(Collectors.toList());
		
		List<MenuModel> menuModels = new ArrayList<MenuModel>();
		
		if(childMenus != null && !childMenus.isEmpty()) {
			MenuModel menuModel = null;
			
			for(Menu childMenu : childMenus) {
				if(childMenu.getHierachyType().equals(HierachyType.Leaf)) {
					menuModel = buildMenuModel(childMenu, activedMenu);
				} else {
					menuModel = buildMenuModel(childMenu, activedMenu);
					menuModel.setChildren(findChildren(menus, childMenu.getId(), activedMenu));
				}
				menuModels.add(menuModel);
			}
		}
		
		return menuModels;
	}

	/*
	protected List<Menu> filterPrivilege(List<Menu> menus)
	{
		List<Menu> result = new ArrayList<Menu>();
		for(int i=0; i<menus.size(); i++) {
			recursiveFilter(i, menus, result, menus.get(i));
		}
		return result.stream().distinct().sorted(sortByHierachyLevelAndSeqNo).collect(Collectors.toList());
	}
	
	protected boolean recursiveFilter(int count, List<Menu> menus, List<Menu> result, Menu current) 
	{
		boolean rtn = false;
		
		for(int i = count; i < menus.size(); i++) {
			if(current.getHierachyType() == HierachyType.Root) {
				if(current.getId().equals(menus.get(i).getRootItemId()) && menus.get(i).getHierachyType() == HierachyType.Leaf) {
					result.add(current);
					break;
				}
				
			} else if(current.getHierachyType() == HierachyType.Middle) {
				if(current.getId().equals(menus.get(i).getParentItemId())) {
					if(menus.get(i).getHierachyType() == HierachyType.Middle) {
						rtn = recursiveFilter(i + 1, menus, result, menus.get(i));
						if(rtn == true) {
							result.add(current);
						}
					} else if(menus.get(i).getHierachyType() == HierachyType.Leaf) {
						result.add(current);
						rtn = true;
						break;
					}
				}
				
			} else {
				result.add(current);
				break;
			}
		}
		
		return rtn;
	}
	*/
	
	protected List<MenuModel> activatedMenuAndActiveHierachies(List<Menu> menus, String activeProgramId, Menu activedMenu)
	{
		if(menus != null && !menus.isEmpty()) {
			List<Menu> activedMenus = null;
			
			if(activedMenu != null) {
				activedMenus = menus.
						stream().
						filter(menu -> menu.getHierachyType() != HierachyType.Leaf).
						filter(menu -> {
							if(menu.getHierachyType() == Menu.HierachyType.Root) {
								if(menu.getId().equals(activedMenu.getRootItemId())) {
									return true;
								}
							} else if(menu.getHierachyType() == Menu.HierachyType.Middle) {
								if(menu.getId().equals(activedMenu.getParentItemId())) {
									return true;
								}
							}
							return false;
						}).
						sorted((menu1, menu2) -> menu1.getSeqNo() - menu2.getSeqNo()).
						collect(Collectors.toList());
				
				activedMenus.add(activedMenu);
			}
			
			if(activedMenus != null && !activedMenus.isEmpty()) {
				return 	activedMenus.
						stream().
						map(menu -> buildMenuModel(menu, activedMenu)).
						collect(Collectors.toList());
			}
		}
		
		return null;
	}
	
	protected MenuModel buildMenuModel(Menu menu, Menu activedMenu)
	{
		MenuModel menuModel = new MenuModel();
		menuModel.setMenuId(menu.getId());
		menuModel.setProgramId(menu.getProgramId());
		menuModel.setItemType(menu.getItemType());
		menuModel.setHierachyType(menu.getHierachyType());
		menuModel.setHierachyLevel(menu.getHierachyLevel());
		menuModel.setSeqNo(menu.getSeqNo());
		menuModel.setTitle(menu.getTitle());
		menuModel.setDescription(menu.getDescription());
		menuModel.setIconType(menu.getIconType());
		if(menu.getIconType() == Menu.IconType.Image) {
			menuModel.setIconImageURI(menu.getIconImageURI());
		} else if(menu.getIconType() == Menu.IconType.AwesomeFont) {
			menuModel.setIconFontClass(menu.getIconFontClass());
		}
		if(menu.getItemType() == ItemType.MenuItem) {
			if(menu.getPlatform() == PlatformValue.web) {
				Web web = webService.getById(menu.getProgramId());
				if(web != null) {
					menuModel.setHref(web.getPath());
				}
			}
		} else {
			if(menu.getPlatform() == PlatformValue.web) {
				if(menu.getDefaultProgramId() != null) {
					Web web = webService.getById(menu.getDefaultProgramId());
					if(web != null) {
						menuModel.setHref(web.getPath());
					}
				}
			}
		}
		menuModel.setActive(false);
		
		/*
		if(menu.getHierachyType() == HierachyType.Root) {
			if(activedMenu != null && activedMenu.getRootItemId() != null && menu.getId().equals(activedMenu.getRootItemId())) {
				menuModel.setActive(true);
			}
		} else if(menu.getHierachyType() == HierachyType.Middle) {
			if(menu.isDefaultOpenMenuDropdown()) {
				menuModel.setActive(true);
			} else {
				if(activedMenu != null && activedMenu.getParentItemId() != null && menu.getId().equals(activedMenu.getParentItemId())) {
					menuModel.setActive(true);
				}
			}
		} else if(menu.getHierachyType() == HierachyType.Leaf) {
			if(activedMenu != null && menu.getId().equals(activedMenu.getId())) {
				menuModel.setActive(true);
			}
		}
		*/
		
		if(menu.getItemType() == ItemType.MenuDropdown) {
			if(menu.isDefaultOpenMenuDropdown()) {
				menuModel.setActive(true);
			} else {
				if(activedMenu != null && activedMenu.getParentItemId() != null && menu.getId().equals(activedMenu.getParentItemId())) {
					menuModel.setActive(true);
				}
			}
		} else if(menu.getItemType() == ItemType.MenuItem) {
			if(activedMenu != null && menu.getId().equals(activedMenu.getId())) {
				menuModel.setActive(true);
			}
		}
		
		return menuModel;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected PlatformValue platform;
		protected UserProfile userProfile;
		protected GrantedAuthority grantedAuthority;
		protected boolean verifyPrivilege = true;
		protected String activeProgramId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<MenuModel> menuModels;
		protected List<MenuModel> activeMenuHierachies;
	}
}