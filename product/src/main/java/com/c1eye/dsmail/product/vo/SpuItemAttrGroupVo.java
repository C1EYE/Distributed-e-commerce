package com.c1eye.dsmail.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SpuItemAttrGroupVo {

    private String groupName;

    private List<Attr> attrs;

}