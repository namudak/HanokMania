package com.seoul.hanokmania.models;

/**
 * Created by student on 2015-10-16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Ray Choe on 2015-10-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)     // 매칭되는 필드가 없을 때 무시
public class Hanok {
    // 등록번호
    public String HANOKNUM;
    // 주소
    public String ADDR;
    // 대지면적
    public String PLOTTAGE;
    // 연면적
    public String TOTAR;
    // 건축면적
    public String BUILDAREA;
    // 층수(지상)
    public String FLOOR;
    // 층수(지하)
    public String FLOOR2;
    // 용도
    public String USE;
    // 구조
    public String STRUCTURE;
    // 평면형식
    public String PLANTYPE;
    // 건립일
    public String BUILDDATE;
    // 비고
    public String NOTE;

    @Override
    public String toString() {
        return "Hanok [등록번호: " + HANOKNUM + ", 주소: " + ADDR + ", 대지면적: " + PLOTTAGE + "]";
    }
}
