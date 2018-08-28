package com.intv.checklist.controllers;

import com.intv.checklist.common.Constants;
import com.intv.checklist.security.CheckListSecurityContext;
import com.intv.checklist.services.ChecklistService;
import com.intv.checklist.vo.CheckListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/checklist/")
public class CheckListController {

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private HttpServletRequest request; //need to double check this one.

    @GetMapping("/checklists/{userId}")
    public List<CheckListVo> getCheckLists(@PathVariable String userId) {

        //UserId may not be required as part of parameter, we can read the userId from logged In user, from SSO/oauth token
        //The logged in user should match the userId being passed to this API. The authorization is done at servlet filter/controller interceptor.
            if(isEmptyOrNull(userId)){
            throw new ValidationException("UserId cannot be null/empty");
        }
        return  checklistService.getCheckListsByUser(userId,getSecurityContext());

    }


    @PostMapping("/create")
    public CheckListVo createCheckList(@Valid @RequestBody CheckListVo checkListVo) {
        if(checkListVo == null || checkListVo.getUser() == null || isEmptyOrNull(checkListVo.getUser().getUserId())){
            throw new ValidationException("UserId cannot be null/empty");
        }
        return checklistService.create(checkListVo,getSecurityContext());
    }

    @PutMapping("/update")
    public CheckListVo updateCheckList( @Valid @RequestBody CheckListVo checkListVo) {
        if(checkListVo == null || checkListVo.getChecklistId() == null){
            throw new ValidationException("ChecklistId cannot be null/empty");
        }
        return  checklistService.update(checkListVo,getSecurityContext());
    }

    @RequestMapping("/delete/{checklistId}")
    @DeleteMapping
    public void deleteCheckList(@PathVariable Long checklistId) {
        if(checklistId == null){
            throw new ValidationException("ChecklistId cannot be null/empty");
        }
        checklistService.delete(checklistId,getSecurityContext());
    }

    @PostMapping("/actionItem/create")
    public CheckListVo createActionItem(@Valid @RequestBody CheckListVo checkListVo) {
        if(checkListVo == null || checkListVo.getChecklistId() == null){
            throw new ValidationException("ChecklistId cannot be null/empty");
        }
        if(checkListVo.getActionItems() == null || checkListVo.getActionItems().size()!=1){
            throw new ValidationException("Action item is missing");
        }
        return checklistService.createActionItem(checkListVo,getSecurityContext());
    }

    @PutMapping("/actionItem/update")
    public CheckListVo updateActionItem(@Valid @RequestBody CheckListVo checkListVo) {
        if(checkListVo == null || checkListVo.getChecklistId() == null){
            throw new ValidationException("ChecklistId cannot be null/empty");
        }
        if(checkListVo.getActionItems() == null || checkListVo.getActionItems().size()!=1){
            throw new ValidationException("Action item is missing");
        }
        return  checklistService.updateActionItem(checkListVo,getSecurityContext());
    }

    @RequestMapping("/actionItem/delete/{actionItemId}")
    @DeleteMapping
    public void deleteActionItem(@PathVariable Long actionItemId) {

        if(actionItemId == null){
            throw new ValidationException("actionItemId cannot be null/empty");
        }
        checklistService.deleteActionItem(actionItemId,getSecurityContext());
    }

    private boolean isEmptyOrNull(String parameter){
        return parameter == null || parameter.isEmpty() ?Boolean.TRUE: Boolean.FALSE;
    }

    private CheckListSecurityContext getSecurityContext(){
        return (CheckListSecurityContext)request.getAttribute(Constants.CHECKLIST_AUTHORIZATION_CONTEXT);
    }


}
