package com.intv.checklist;

import com.intv.checklist.security.CheckListSecurityContext;
import com.intv.checklist.services.ChecklistService;
import com.intv.checklist.spring.EmbeddedApplication;
import com.intv.checklist.vo.CheckListVo;
import com.intv.checklist.vo.UserVo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = EmbeddedApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class CheclistServiceTest {

    @Autowired
    private ChecklistService checklistService;

    @Test
    public void createCheckList(){
        CheckListVo checkListVo = new CheckListVo();
        checkListVo.setName("My checklist");
        UserVo userVo = new UserVo();
        userVo.setUserId("user1");
        checkListVo.setUser(userVo);
        checklistService.create(checkListVo,new CheckListSecurityContext(Arrays.asList("user1")));


    }

    @Test
    @Ignore
    public void findChecklistsByUser(){
        List<CheckListVo> checkListVos = checklistService.getCheckListsByUser("user1",new CheckListSecurityContext(Arrays.asList("user1")));
        Assert.assertEquals(1,checkListVos.size());

    }
}
