package com.amswh.iLIMS.domain;


import lombok.Data;

import java.util.Map;

@Data
public class UserLoginStatus {

      public int code=0;
      public String message=null;
      public Map<String,String> userInfo;
}
