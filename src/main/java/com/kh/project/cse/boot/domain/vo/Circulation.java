package com.kh.project.cse.boot.domain.vo;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Circulation {
    private int circulationNo;
    private int storeNo;
    private int productNo;
    private int inputPrice;
    private int salePrice;
    private int circulationAmount;
    private Date circulationDate;
    private int status;


    private String storeName;

    //발주목록 가져오기
    private String setNo;
    private int rowNum;
    private int rnum;
    private String minuteGroup;
    private int totalAmount;
    private int totalInputPrice;
}
