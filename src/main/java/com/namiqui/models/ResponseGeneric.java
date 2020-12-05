package com.namiqui.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class ResponseGeneric implements Serializable {
    private ErrorDTO errors;
    private Object data;
    private Date timestamp;
    private Integer codeStatus;
}
