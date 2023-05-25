package com.otc.kws.core.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "sys_m_line_notify_channel")
@Data
public class LineNotifyChannel extends BaseKwsEntity
{
    @Id
    @Column(name = "id")
    protected String id;

    @Column(name = "channel_name")
    protected String channelName;

    @Column(name = "access_token")
    protected String accessToken;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    protected MasterStatusValue status;

    @Column(name = "created_by")
    protected String createdBy;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @Column(name = "last_modified_by")
    protected String lastModifiedBy;

    @Column(name = "last_modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedAt;
}