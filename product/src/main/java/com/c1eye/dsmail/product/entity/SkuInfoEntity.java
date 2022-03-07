package com.c1eye.dsmail.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * skuÐÅÏ¢
 * 
 * @author c1eye
 * @email c1eyemmj@gmail.com
 * @date 2022-03-07 13:54:48
 */
@Data
@TableName("pms_sku_info")
public class SkuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * skuId
	 */
	@TableId
	private Long skuId;
	/**
	 * spuId
	 */
	private Long spuId;
	/**
	 * skuÃû³Æ
	 */
	private String skuName;
	/**
	 * sku½éÉÜÃèÊö
	 */
	private String skuDesc;
	/**
	 * ËùÊô·ÖÀàid
	 */
	private Long catalogId;
	/**
	 * Æ·ÅÆid
	 */
	private Long brandId;
	/**
	 * Ä¬ÈÏÍ¼Æ¬
	 */
	private String skuDefaultImg;
	/**
	 * ±êÌâ
	 */
	private String skuTitle;
	/**
	 * ¸±±êÌâ
	 */
	private String skuSubtitle;
	/**
	 * ¼Û¸ñ
	 */
	private BigDecimal price;
	/**
	 * ÏúÁ¿
	 */
	private Long saleCount;

}
