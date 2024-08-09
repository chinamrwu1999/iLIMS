package com.amswh.iLIMS.project.domain.survey;

import lombok.Data;

@Data
public class Choice {
    private String text="";
    private String tip="";
    private String input="";
    private Boolean choice=Boolean.FALSE;
}
