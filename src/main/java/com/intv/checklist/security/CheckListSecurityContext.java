package com.intv.checklist.security;

import java.util.ArrayList;
import java.util.List;

public class CheckListSecurityContext {

    //Add any other entitlements related into this context
    private String loggedInUser;

    //This is done only to by pass the authorization check
    private List<String> userIds = new ArrayList<>();


   public CheckListSecurityContext(List<String> userIds){
        this.userIds = userIds;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


}
