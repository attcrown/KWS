package com.otc.kws.fishsix.lib.gateway.web.api.course_order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrder;
import com.otc.kws.fishsix.lib.domain.service.course_order.CourseOrderService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/course-order/history/load")
public class FishsixCourseOrderHistoryLoadAPI extends BaseKwsFishsixAPI
{
	@Autowired
    protected CourseOrderService courseOrderService;


    @PostMapping
    public ResponseEntity<?> loadHistoryCourseOrder(HttpServletRequest request, @RequestBody FishsixCourseOrderHistoryLoadAPI.RequestMessage requestMessage)
    {
        logger.info("### {}.loadHistoryCourseOrder ###", getClass().getSimpleName());
        logger.info("requestMessage => {}", requestMessage);
        
        List<CourseOrder> courseOrders = courseOrderService.loadCourseOrderHistories(req -> {
        	setupServiceRequest(request, req);
        	req.setCourseId(requestMessage.getCourseId());
        	req.setStudentId(requestMessage.getStudentId());
        }).getCourseOrders();

        FishsixCourseOrderHistoryLoadAPI.BodyResponseMessage bodyResponseMessage = new FishsixCourseOrderHistoryLoadAPI.BodyResponseMessage();
        bodyResponseMessage.setCourseOrders(courseOrders);

        return replySuccess(request, bodyResponseMessage);
    }


    @Data
    public static class RequestMessage
    {
    	protected String courseId;
        protected String studentId;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
        protected List<CourseOrder> courseOrders;
    }
}