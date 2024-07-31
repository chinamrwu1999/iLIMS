package com.amswh.iLIMS.project.domain.survey;

import lombok.Data;

@Data
public class Choice {
    private String text="";
    private String detail="";
    private Boolean choice=Boolean.FALSE;
}
