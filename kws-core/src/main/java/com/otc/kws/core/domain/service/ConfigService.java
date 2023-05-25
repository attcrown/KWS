package com.otc.kws.core.domain.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.FileStorageTypeValue;
import com.otc.kws.core.domain.constant.value.ModuleValue;
import com.otc.kws.core.domain.entity.Config;
import com.otc.kws.core.domain.repository.jpa.JpaConfigRepository;

@Service
public class ConfigService extends CacheKwsService<Config, Config.CompositeId>
{
	@Autowired
	protected JpaConfigRepository configRepository;
	
	
	@Override
	public List<Config> loadDatas() 
	{
		return configRepository.findAll();
	}
	
	@Override
	protected Config.CompositeId extractId(Config entity) 
	{
		return new Config.CompositeId(entity.getModule(), entity.getCategory(), entity.getName());
	}
	
	
	public Config getByModuleAndCategoryAndName(String module, String category, String name)
	{
		return getAll().
				stream().
				filter(config -> config.getModule().equals(module)).
				filter(config -> config.getCategory().equals(category)).
				filter(config -> config.getName().equals(name)).
				findFirst().
				orElse(null);
	}
	
	
	public String getDefaultLabelLanguageId()
	{
		return "th";
	}
	
	
	// ****************************** line ****************************** //
	public String getLineNotifyEndpoint()
	{
		Config config = getByModuleAndCategoryAndName(ModuleValue.core.name(), "line", "notify_endpoint");
		if(config != null && config.getValue() != null) {
			return config.getValue();
		}
		return "https://notify-api.line.me/api/notify";
	}
	// ****************************** line ****************************** //
	
	
	// ****************************** web ****************************** //
	public String getWebTemplate(String runtimeModule)
	{
		return "admin-lte-3";
	}
	
	public String getWebFragmentNavbarFile(String runtimeModule)
	{
		return runtimeModule + "/${webTemplate}/fragment/navbar.html".replace("${webTemplate}", getWebTemplate(runtimeModule));
	}
	
	public String getWebFragmentSidebarFile(String runtimeModule)
	{
		return runtimeModule + "/${webTemplate}/fragment/sidebar.html".replace("${webTemplate}", getWebTemplate(runtimeModule));
	}
	
	public String getWebFragmentFooterFile(String runtimeModule)
	{
		return runtimeModule + "/${webTemplate}/fragment/footer.html".replace("${webTemplate}", getWebTemplate(runtimeModule));
	}
	
	public String getWebFragmentWorkspaceHeaderFile(String runtimeModule)
	{
		return runtimeModule + "/${webTemplate}/fragment/workspace_header.html".replace("${webTemplate}", getWebTemplate(runtimeModule));
	}
	// ****************************** web ****************************** //
	
	
	// ****************************** resource ****************************** //
	public String getResourceWebPrefixPath()
	{
		Config config = getByModuleAndCategoryAndName(ModuleValue.core.name(), "resource", "web_prefix_path");
		if(config != null && config.getValue() != null) {
			return config.getValue();
		}
		return null;
	}
	
	public String getResourceFileLocation()
	{
		Config config = getByModuleAndCategoryAndName(ModuleValue.core.name(), "resource", "file_location");
		if(config != null && config.getValue() != null) {
			return config.getValue().replace("${core.resource.filesystem_path}", getFileSystemBaseResourcePath());
		}
		return null;
	}
	
	public String getFileSystemBaseResourcePath()
	{
		Config config = getByModuleAndCategoryAndName(ModuleValue.core.name(), "resource", "filesystem_path");
		if(config != null && config.getValue() != null) {
			return config.getValue();
		}
		return null;
	}
	
	public String getFileSystemFirstJobberResourcePath()
	{
		Config config = getByModuleAndCategoryAndName(ModuleValue.first_jobber.name(), "resource", "filesystem_path");
		if(config != null && config.getValue() != null) {
			return config.getValue().replace("${core.resource.filesystem_path}", getFileSystemBaseResourcePath());
		}
		return null;
	}
	// ****************************** resource ****************************** //
	
	
	// ****************************** fileStore ****************************** //
	public FileStorageTypeValue getFileStorageType()
	{
		Config config = getByModuleAndCategoryAndName(ModuleValue.core.name(), "filestore", "storage_type");
		if(config != null && config.getValue() != null) {
			return FileStorageTypeValue.valueOf(config.getValue());
		}
		return null;
	}
	// ****************************** fileStore ****************************** //
	
	
	
	// ****************************** firstjobber ****************************** //
	public List<String> getFirstJobberInternshipRegistrationEducationLevels()
	{
		Config config = getByModuleAndCategoryAndName(ModuleValue.first_jobber.name(), "registration", "internship.educationLevel");
		if(config != null && config.getValue() != null) {
			return Arrays.asList(config.getValue().split(","));
		}
		return null;
	}
	// ****************************** firstjobber ****************************** //
}