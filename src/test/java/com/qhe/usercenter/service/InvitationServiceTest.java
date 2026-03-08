package com.qhe.usercenter.service;

import com.qhe.usercenter.constant.UserConstants;
import com.qhe.usercenter.model.InvitationCode;
import com.qhe.usercenter.model.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class InvitationServiceTest {

    @Resource
    InvitationCodeService invitationCodeService;

    @Autowired
    private HttpServletRequest request;

    @Test
    public void saveTest() {
        InvitationCode invitationCode = new InvitationCode();
        invitationCode.setInvitationCode(UUID.randomUUID().toString());
        invitationCode.setInvitationType(0);
        invitationCodeService.save(invitationCode);
    }

    @Test
    public void createTest() {
        InvitationCode invitationCode = new InvitationCode();
        invitationCode.setInvitationType(0);
        invitationCode.setInvitationNote("");
        invitationCode.setExpireTime(null);
        UserVO user = new UserVO();
        user.setUserId(2016942372077625346L);
        request.getSession().setAttribute(UserConstants.USER_LOGIN_STATE, user);
        invitationCodeService.createInvitation(invitationCode, request);
    }

    @Test
    public void searchTest() {
        List<InvitationCode> search = invitationCodeService.search(null);
        search.forEach(System.out::println);
    }

    @Test
    public void updateTest() {
        // 更新修改人信息
        InvitationCode invitationCode = new InvitationCode();
        invitationCode.setInvitationId(2030198430093135874L);
        invitationCode.setInvitationStatus(1);
        invitationCode.setInvitationType(1);
        invitationCode.setInvitationNote("更新测试");
        invitationCode.setExpireTime(new Date());
        invitationCode.setUpdateId(2016942372077625346L);
        invitationCodeService.updateById(invitationCode);
    }

    @Test
    public void removeTest() {
        // 更新修改人信息
        invitationCodeService.removeById(2030198430093135874L);
    }
}
