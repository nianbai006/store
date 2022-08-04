package com.cy.store.util;

import lombok.Data;

import java.io.Serializable;
@Data
public class JsonResult<E> implements Serializable {
    //状态码
    private Integer state;
    private String message;
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }
    public JsonResult(Throwable e){
        this.message = e.getMessage();
    }
}
