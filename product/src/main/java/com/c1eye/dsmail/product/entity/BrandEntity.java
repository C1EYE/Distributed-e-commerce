package com.c1eye.dsmail.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.c1eye.common.valid.AddGroup;
import com.c1eye.common.valid.ListValue;
import com.c1eye.common.valid.UpdateGroup;
import com.c1eye.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * Æ·ÅÆ
 * 
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:49
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Æ·ÅÆid
	 */
	@TableId
	@NotNull(message = "修改必须指定品牌id",groups = {UpdateGroup.class})
	@Null(message = "新增不能指定ID",groups = {AddGroup.class})
	private Long brandId;
	/**
	 * Æ·ÅÆÃû
	 */
	@NotBlank(message = "品牌名必须提交",groups = {AddGroup.class})
	private String name;
	/**
	 * Æ·ÅÆlogoµØÖ·
	 */
	@URL(message = "logo 必须是一个合法的 url 地址",groups = {AddGroup.class,UpdateGroup.class})
	@NotEmpty(groups = {AddGroup.class})
	private String logo;
	/**
	 * ½éÉÜ
	 */
	private String descript;
	/**
	 * ÏÔÊ¾×´Ì¬[0-²»ÏÔÊ¾£»1-ÏÔÊ¾]
	 */
	@NotNull(groups = {UpdateStatusGroup.class})
	@ListValue(groups = {AddGroup.class,UpdateGroup.class, UpdateStatusGroup.class},vals = {0,1})
	private Integer showStatus;
	/**
	 * ¼ìË÷Ê××ÖÄ¸
	 */
	@NotEmpty(groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$",message = "检索首字母必须是一个字母",groups = {AddGroup.class,UpdateGroup.class})
	private String firstLetter;
	/**
	 * ÅÅÐò
	 */
	@NotNull(groups = {AddGroup.class})
	@Min(value = 0)
	private Integer sort;

}
