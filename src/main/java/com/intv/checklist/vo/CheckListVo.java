package com.intv.checklist.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class CheckListVo {

    private Long checklistId;
    private String name;
    private UserVo user;
    private List<ActionItemVo> actionItems;

    public CheckListVo(){

    }


    public Long getChecklistId() {
        return checklistId;
    }

    public void addActionItem(ActionItemVo actionItemVo){
        if(actionItems ==null){
            actionItems = new ArrayList<>();
        }
        actionItems.add(actionItemVo);
    }

    public void setChecklistId(Long checklistId) {
        this.checklistId = checklistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public List<ActionItemVo> getActionItems() {
        return actionItems;
    }

    public void setActionItems(List<ActionItemVo> actionItems) {
        this.actionItems = actionItems;
    }

    public static class ActionItemVo {

        private Long actionItemId;
        private String description;
        private boolean completed;
        private String createdOn;
        private String lastUpdatedOn;
        private boolean deleted;


        public ActionItemVo(){

        }

        public Long getActionItemId() {
            return actionItemId;
        }

        public void setActionItemId(Long actionItemId) {
            this.actionItemId = actionItemId;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getLastUpdatedOn() {
            return lastUpdatedOn;
        }

        public void setLastUpdatedOn(String lastUpdatedOn) {
            this.lastUpdatedOn = lastUpdatedOn;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}
