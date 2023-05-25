package com.otc.kws.fishsix.lib.domain.service.course_order;

import com.otc.kws.core.domain.service.BaseKwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
public class CourseOrderService extends BaseKwsService
{
    @Autowired
    protected CourseOrderCreateCommand courseOrderCreateCommand;
    
    @Autowired
    protected CourseOrderLoadHistoryCommand courseOrderLoadHistoryCommand;


    // ****************************** createCourseOrder ****************************** //
    @Transactional
    public CourseOrderCreateCommand.Response createCourseOrder(Consumer<CourseOrderCreateCommand.Request> consumer)
    {
        CourseOrderCreateCommand.Request request = new CourseOrderCreateCommand.Request();
        consumer.accept(request);
        return createCourseOrder(request);
    }

    @Transactional
    public CourseOrderCreateCommand.Response createCourseOrder(CourseOrderCreateCommand.Request request)
    {
        logger.info("### {}.createCourseOrder ###", getClass().getSimpleName());
        return courseOrderCreateCommand.createCourseOrder(request);
    }
    // ****************************** createCourseOrder ****************************** //
    
    
 // ****************************** loadCourseOrderHistories ****************************** //
    @Transactional
    public CourseOrderLoadHistoryCommand.Response loadCourseOrderHistories(Consumer<CourseOrderLoadHistoryCommand.Request> consumer)
    {
    	CourseOrderLoadHistoryCommand.Request request = new CourseOrderLoadHistoryCommand.Request();
        consumer.accept(request);
        return loadCourseOrderHistories(request);
    }

    @Transactional
    public CourseOrderLoadHistoryCommand.Response loadCourseOrderHistories(CourseOrderLoadHistoryCommand.Request request)
    {
        logger.info("### {}.loadCourseOrderHistories ###", getClass().getSimpleName());
        return courseOrderLoadHistoryCommand.loadCourseOrderHistories(request);
    }
    // ****************************** loadCourseOrderHistories ****************************** //
}