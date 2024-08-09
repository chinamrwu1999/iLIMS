package com.amswh.iLIMS.project.domain.survey;

import lombok.Data;

import java.util.List;

@Data
public class Survey {

    private  String title;
    private String version;
    private List<Risk> risks;
}
