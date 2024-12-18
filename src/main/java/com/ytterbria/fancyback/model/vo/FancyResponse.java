package com.ytterbria.fancyback.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FancyResponse implements Serializable {

    private String generatedChart;

    private String generatedResult;

    private static final long serialVersionUID = 1L;
}
