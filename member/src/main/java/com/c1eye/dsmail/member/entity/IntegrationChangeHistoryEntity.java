package com.c1eye.dsmail.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * »ý·Ö±ä»¯ÀúÊ·¼ÇÂ¼
 * 
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 15:58:46
 */
@Data
@TableName("ums_integration_change_history")
public class IntegrationChangeHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member_id
	 */
	private Long memberId;
	/**
	 * create_time
	 */
	private Date createTime;
	/**
	 * ±ä»¯µÄÖµ
	 */
	private Integer changeCount;
	/**
	 * ±¸×¢
	 */
	private String note;
	/**
	 * À´Ô´[0->¹ºÎï£»1->¹ÜÀíÔ±ÐÞ¸Ä;2->»î¶¯]
	 */
	private Integer sourceTyoe;

}
