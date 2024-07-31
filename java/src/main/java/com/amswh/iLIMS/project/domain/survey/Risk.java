package com.amswh.iLIMS.project.domain.survey;

import lombok.Data;

import java.util.List;

@Data
public class Risk {

    private String name;
    private List<Choice> choices;
}
