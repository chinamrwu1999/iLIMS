package com.amswh.iLIMS.project.domain.survey;

import lombok.Data;

import java.util.List;

@Data
public class Risk {

    private String name;
    private String questionId;
    private String question="";
    private List<Choice> choices;
}
