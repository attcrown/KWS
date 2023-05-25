package com.otc.kws.fishsix.lib.gateway.web.api.course_order;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrder;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.service.course_order.CourseOrderCreateCommand;
import com.otc.kws.fishsix.lib.domain.service.course_order.CourseOrderService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/course-order/create")
public class FishsixCourseOrderCreateAPI extends BaseKwsFishsixAPI
{
    @Autowired
    protected CourseOrderService courseOrderService;


    @PostMapping
    public ResponseEntity<?> createCourseOrder(HttpServletRequest request, @RequestBody FishsixCourseOrderCreateAPI.RequestMessage requestMessage)
    {
        logger.info("### {}.createCourseOrder ###", getClass().getSimpleName());
        logger.info("requestMessage => {}", requestMessage);

        CourseOrderCreateCommand.Response createCourseOrderResponse = courseOrderService.createCourseOrder(req -> {
            setupServiceRequest(request, req);
            req.setCourseOrder(requestMessage.getCourseOrder());
        });

        FishsixCourseOrderCreateAPI.BodyResponseMessage bodyResponseMessage = new FishsixCourseOrderCreateAPI.BodyResponseMessage();
        bodyResponseMessage.setCreatedCourseOrder(createCourseOrderResponse.getCreatedCourseOrder());
        bodyResponseMessage.setCreatedStudentCourseQuota(createCourseOrderResponse.getCreatedStudentCourseQuota());

        return replySuccess(request, bodyResponseMessage);
    }


    @Data
    public static class RequestMessage
    {
        protected CourseOrder courseOrder;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
        protected CourseOrder createdCourseOrder;
        protected StudentCourseQuota createdStudentCourseQuota;
    }
}