package com.intv.checklist.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Table(name = "ACTION_ITEM")
public class ActionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actionItemId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COMPLETED")
    private Boolean completed;

    @Column(name = "CREATED_ON")
    @CreationTimestamp
    private LocalDateTime createOn;

    @Column(name = "LAST_UPDATED_ON")
    @UpdateTimestamp
    private LocalDateTime lastUpdatedOn;

    @Column(name ="IS_DELETED")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "CHECKLIST_ID")
    private Checklist  checklist;

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public LocalDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getActionItemId() {
        return actionItemId;
    }

    public void setActionItemId(Long actionItemId) {
        this.actionItemId = actionItemId;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionItem that = (ActionItem) o;
        return Objects.equals(actionItemId, that.actionItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionItemId);
    }
}
