package com.otc.kws.core.gateway.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.otc.kws.core.domain.entity.BaseKwsEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public abstract class CacheReloadKwsAPI <E extends BaseKwsEntity> extends BaseKwsAPI
{
	protected abstract List<E> reloadCache();
	
	@PostMapping("/cache/reload")
	public ResponseEntity<ResponseMessage<BodyResponseMessage<E>>> reloadCache(HttpServletRequest request)
	{
		return replySuccess(request, new BodyResponseMessage<E>(reloadCache()));
	}
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BodyResponseMessage <E extends BaseKwsEntity> extends ResponseMessage.BaseBody
	{
		protected List<E> datas;
	}
}