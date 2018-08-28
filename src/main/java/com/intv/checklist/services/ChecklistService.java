package com.intv.checklist.services;

import com.intv.checklist.security.CheckListSecurityContext;
import com.intv.checklist.vo.CheckListVo;

import java.util.List;

public interface  ChecklistService {

    List<CheckListVo> getCheckListsByUser(String userId, CheckListSecurityContext checkListSecurityContext);

    CheckListVo create(CheckListVo checkListVo,CheckListSecurityContext checkListSecurityContext);

    CheckListVo update(CheckListVo checkListVo,CheckListSecurityContext checkListSecurityContext);

    void delete(Long checkListId,CheckListSecurityContext checkListSecurityContext);

    CheckListVo createActionItem(CheckListVo checkListVo,CheckListSecurityContext checkListSecurityContext);

    CheckListVo updateActionItem(CheckListVo checkListVo,CheckListSecurityContext checkListSecurityContext);

    void deleteActionItem(Long actionItemId,CheckListSecurityContext checkListSecurityContext);

}