package com.c1eye.dsmail.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author c1eye
 * time 2022/3/16 14:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catelog2Vo implements Serializable {
    private String catalog1Id;
    private List<Catelog3Vo> catalog3List;
    private String id;
    private String name;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catelog3Vo implements Serializable{
        private String catalog2Id;
        private String id;
        private String name;
    }
}
