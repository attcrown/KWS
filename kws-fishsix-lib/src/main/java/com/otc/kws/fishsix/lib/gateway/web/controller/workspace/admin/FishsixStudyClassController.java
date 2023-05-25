package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassChannel;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassLocation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassType;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassChannelRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassLocationRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassTypeRepository;
import com.otc.kws.fishsix.lib.domain.service.class_schedule.StudyClassScheduleTemplateService;
import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/study-class")
public class FishsixStudyClassController extends KwsFishsixWorkspaceController
{
	@Autowired
	protected JpaStudyClassChannelRepository studyClassChannelRepository;
	
	@Autowired
	protected JpaStudyClassTypeRepository studyClassTypeRepository;
	
	@Autowired
	protected JpaStudyClassLocationRepository studyClassLocationRepository;
	
	@Autowired
	protected StudyClassScheduleTemplateService studyClassScheduleTemplateService;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		Date now = new Date();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		CompletableFuture<List<StudyClassChannel>> classChannelsFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassChannelRepository.findAllActive();
		});
		
		CompletableFuture<List<StudyClassType>> classTypesFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassTypeRepository.findAllActive();
		});
		
		CompletableFuture<List<StudyClassLocation>> classLocationsFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassLocationRepository.findAllActive();
		});
		
		CompletableFuture<List<StudyClassScheduleTemplate>> studyClassScheduleTemplatesFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassScheduleTemplateService.loadStudyClassScheduleTemplate(req -> {
				setupServiceRequest(request, req);
				req.setDate(now);
			}).getStudyClassScheduleTemplates();
		});
		
		CompletableFuture.allOf(classChannelsFuture, classTypesFuture, classLocationsFuture, studyClassScheduleTemplatesFuture);
		
		try {
			model.addAttribute("today", sdf.format(now));
			model.addAttribute("classChannels", classChannelsFuture.get());
			model.addAttribute("classTypes", classTypesFuture.get());
			model.addAttribute("classLocations", classLocationsFuture.get());
			model.addAttribute("studyClassScheduleTemplates", studyClassScheduleTemplatesFuture.get());
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
	}
}