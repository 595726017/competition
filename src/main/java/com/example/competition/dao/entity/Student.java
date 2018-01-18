package com.example.competition.dao.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
@DynamicUpdate
@Entity
@Data
public class Student {
    @Id
    private Integer studentId;

    private String studentNo;

    private String studentName;

    private Integer groupId;

    private Integer studentSpecialtyId;

    private Integer studentGrade;

    private Integer studentStatus;

    private Date createTime;

    private Date updateTime;

}