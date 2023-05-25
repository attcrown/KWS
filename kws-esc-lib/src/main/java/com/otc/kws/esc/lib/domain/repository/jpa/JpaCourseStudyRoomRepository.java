package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.CourseStudyRoom;

@Repository
public interface JpaCourseStudyRoomRepository extends KwsEscJpaRepository<CourseStudyRoom, String>
{

}