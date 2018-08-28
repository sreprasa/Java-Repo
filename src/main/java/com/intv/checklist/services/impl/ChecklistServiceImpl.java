package com.intv.checklist.services.impl;

import com.intv.checklist.domain.ActionItem;
import com.intv.checklist.domain.Checklist;
import com.intv.checklist.domain.User;
import com.intv.checklist.exceptions.AuthroizationException;
import com.intv.checklist.exceptions.ResourceNotFoundException;
import com.intv.checklist.exceptions.ValidationException;
import com.intv.checklist.repositories.ActionItemRepository;
import com.intv.checklist.repositories.CheckListRepository;
import com.intv.checklist.repositories.UserRepository;
import com.intv.checklist.security.CheckListSecurityContext;
import com.intv.checklist.services.ChecklistService;
import com.intv.checklist.vo.CheckListVo;
import com.intv.checklist.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChecklistServiceImpl implements ChecklistService {

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private ActionItemRepository actionItemRepository;

    @Autowired
    private UserRepository userRepository;

    private Logger LOGGER = LoggerFactory.getLogger(ChecklistServiceImpl.class);


    //TODO : Need to add pagination support
    @Override
    public List<CheckListVo> getCheckListsByUser(String userId, CheckListSecurityContext checkListSecurityContext) {
        Checklist checklist = new Checklist();
        checklist.setDeleted(Boolean.FALSE);
        List<Checklist> checklists = checkListRepository.findAll(Example.of(checklist));
        List<CheckListVo> checkListVos = new ArrayList<>();
        checklists.forEach(c -> {
            checkListVos.add(getCheckListVo(c));

        });
        return checkListVos;
    }

    @Override
    @Transactional
    public CheckListVo create(CheckListVo checkListVo,CheckListSecurityContext checkListSecurityContext) {
        User user = getUser(checkListVo.getUser().getUserId());
        if(user == null){
            throw new ResourceNotFoundException(String.format("User with Id [%s] doesn't exists",checkListVo.getUser().getUserId()));
        }
        Checklist checklist = new Checklist();
        checklist.setDeleted(Boolean.FALSE);
        checklist.setName(checkListVo.getName());
        checklist.setUser(user);
        checkListRepository.save(checklist);
        LOGGER.info("created new checklist for user [{}]",user.getUserId());
        return getCheckListVo(checklist);

    }

    @Override
    @Transactional
    public CheckListVo update(CheckListVo checkListVo,CheckListSecurityContext checkListSecurityContext) {
        Checklist checklist = getCheckListById(checkListVo.getChecklistId());
        validateCheckList(checklist,checkListSecurityContext);
        checklist.setName(checkListVo.getName());
        LOGGER.info("Updated checklist [{}]",checkListVo.getChecklistId());
        return getCheckListVo(checklist);
    }



    @Override
    @Transactional
    public void delete(Long checkListId,CheckListSecurityContext checkListSecurityContext) {
        Checklist checklist = getCheckListById(checkListId);
        validateAuthorization(checklist,checkListSecurityContext);
        if(checklist.getActionItems()!=null) {
            checklist.getActionItems().stream().forEach(a -> deleteActionItem(a.getActionItemId(),checkListSecurityContext));
        }
        checklist.setDeleted(Boolean.TRUE);
        LOGGER.info("Deleted checklist [{}]",checkListId);
    }

    @Override
    @Transactional
    public CheckListVo createActionItem(CheckListVo checkListVo,CheckListSecurityContext checkListSecurityContext) {
        Checklist checkList = getCheckListById(checkListVo.getChecklistId());
        validateCheckList(checkList,checkListSecurityContext);
        CheckListVo.ActionItemVo actionItemVo = checkListVo.getActionItems().stream().findFirst().get();
        if(actionItemVo.isCompleted()){
            throw new ValidationException("Action item cannot be in completed state during creation");
        }
        ActionItem actionItem = new ActionItem();
        actionItem.setDeleted(Boolean.FALSE);
        actionItem.setDescription(actionItemVo.getDescription());
        actionItem.setCompleted(Boolean.FALSE);
        actionItem.setChecklist(checkList);
        actionItemRepository.save(actionItem);
        LOGGER.info("Creation of new action item [{}]",actionItem.getActionItemId());
        return getCheckListVo(getCheckListById(checkListVo.getChecklistId()));

    }



    @Override
    @Transactional
    public CheckListVo updateActionItem(CheckListVo checkListVo,CheckListSecurityContext checkListSecurityContext) {
        Checklist checkList = getCheckListById(checkListVo.getChecklistId());
        validateCheckList(checkList,checkListSecurityContext);
        CheckListVo.ActionItemVo actionItemVo = checkListVo.getActionItems().stream().findFirst().get();
        ActionItem actionItem = getActionItemById(actionItemVo.getActionItemId());
        validateActionItem(actionItem,checkListSecurityContext);
        actionItem.setCompleted(actionItemVo.isCompleted());
        actionItem.setDescription(actionItemVo.getDescription());
        LOGGER.info("Updated action Item [{}]",actionItem.getActionItemId());
        return  getCheckListVo(checkList);
    }

    @Override
    @Transactional
    public void deleteActionItem(Long actionItemId,CheckListSecurityContext checkListSecurityContext) {
        ActionItem actionItem = getActionItemById(actionItemId);
        validateAuthorization(actionItem,checkListSecurityContext);
        actionItem.setDeleted(Boolean.TRUE);
        LOGGER.info("Deleted action item [{}]",actionItemId);
    }

    private void validateAuthorization(Checklist checklist, CheckListSecurityContext checkListSecurityContext) {
        if(checklist!=null){
            if(!checkListSecurityContext.getUserIds().contains(checklist.getUser().getUserId())){
                throw new AuthroizationException(String.format("User %s doesn't have access to this checklist"));
            }
        }
    }

    private void validateAuthorization(ActionItem actionItem, CheckListSecurityContext checkListSecurityContext) {
        if(actionItem!=null){
            if(!checkListSecurityContext.getUserIds().contains(actionItem.getChecklist().getUser().getUserId())){
                throw new AuthroizationException(String.format("User %s doesn't have access to this action item"));
            }
        }
    }

    private void validateActionItem(ActionItem actionItem, CheckListSecurityContext checkListSecurityContext){
        validateDelete(actionItem);
        validateAuthorization(actionItem,checkListSecurityContext);
    }

    private void validateCheckList(Checklist checklist, CheckListSecurityContext checkListSecurityContext){
        validateDelete(checklist);
        validateAuthorization(checklist,checkListSecurityContext);
    }

    private void validateDelete(Checklist checkList) {
        if(checkList.getDeleted()){
            String msg = String.format("Given check list [%d] is deleted",checkList.getChecklistId());
            LOGGER.warn(msg);
            throw new ValidationException(msg);
        }
    }

    private void validateDelete(ActionItem actionItem) {
        if(actionItem.getDeleted()){
            String msg = String.format("Given action Item [%d] is deleted",actionItem.getActionItemId());
            LOGGER.warn(msg);
            throw new ValidationException(msg);
        }
    }

    private Checklist getCheckListById(Long checkListId) {
        Checklist checkList = checkListRepository.findById(checkListId).orElse(null);
        if(checkList == null){
            throw new ResourceNotFoundException(String.format("Checklist with Id [%d] doesn't exist",checkListId));
        }
        return checkList;
    }

    private ActionItem getActionItemById(Long actionItemId) {
        ActionItem actionItem = actionItemRepository.findById(actionItemId).orElse(null);
        if(actionItem == null){
            throw new ResourceNotFoundException(String.format("Action item with Id [%d] doesn't exist",actionItemId));
        }
        return actionItem;
    }

    private CheckListVo getCheckListVo(Checklist c) {
        CheckListVo checkListVo = new CheckListVo();
        checkListVo.setName(c.getName());
        checkListVo.setChecklistId(c.getChecklistId());
        checkListVo.setUser(getUserVo(c.getUser()));
        if(c.getActionItems()!=null) {
            c.getActionItems().forEach(a -> checkListVo.addActionItem(getActionItemVo(a)));
        }
        return checkListVo;
    }

    private CheckListVo.ActionItemVo getActionItemVo(ActionItem a) {
        CheckListVo.ActionItemVo actionItemVo = new CheckListVo.ActionItemVo();
        actionItemVo.setActionItemId(a.getActionItemId());
        actionItemVo.setDescription(a.getDescription());
        actionItemVo.setCompleted(a.isCompleted());
        actionItemVo.setDeleted(a.getDeleted());
        actionItemVo.setCreatedOn(a.getCreateOn().toString());
        actionItemVo.setLastUpdatedOn(a.getLastUpdatedOn().toString());
        return actionItemVo;
    }

    private UserVo getUserVo(User user) {
        UserVo userVo = new UserVo();
        userVo.setUserId(user.getUserId());
        userVo.setEmailId(user.getEmail());
        userVo.setFullName(user.getName());
        return userVo;
    }

    private User getUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }


}
