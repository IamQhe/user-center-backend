package com.qhe.usercenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.qhe.usercenter.mapper.UserMapper;
import com.qhe.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Resource
    UserService userService;

    @Resource
    UserMapper userMapper;

    @Test
    public void saveTest() {
        User user = new User();
        user.setUserName("清和");
        user.setUserAccount("qhe");
        user.setUserPassword("123456");
        user.setUserAvatarUrl("https://i1.hdslb.com/bfs/face/0f9bc1e281fda5070a47f883a59465c11f62a949.jpg@96w_96h");
        user.setUserRole(1);
        user.setUserEmail("2423414458@qq.com");
        user.setUserGender(1);
        user.setUserStatus(1);
        user.setInvitationCode("1");
        boolean save = userService.save(user);
        Assert.isTrue(save, "save error");
    }

    @Test
    public void searchTest() {
        List<User> list = userMapper.selectList(null);
        System.out.println("list counts: " + list.size());
        list.forEach(System.out::println);
    }

    @Test
    public void logicDeleteTest() {
        String userAccount = "qhe";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        boolean remove = userService.remove(queryWrapper);
        if (remove) {
            System.out.println("remove success.");
        }
        List<User> list = userService.list();
        System.out.println("list counts: " + list.size());
        list.forEach(System.out::println);
    }

    @Test
    public void registerTest() {
        // 非空
        Long result;
        User user = new User();
        user.setUserAccount("");
        user.setUserPassword("123456");
        user.setCheckPassword("123456");
        user.setInvitationCode("2423");
        result = userService.register(user.getUserAccount(),
                user.getUserPassword(),
                user.getCheckPassword(),
                user.getInvitationCode());
        Assertions.assertEquals(-1L, result);

        // 用户名格式
        user.setUserAccount("qh.a");
        user.setUserPassword("123456");
        user.setCheckPassword("123456");
        result = userService.register(user.getUserAccount(),
                user.getUserPassword(),
                user.getCheckPassword(),
                user.getInvitationCode());
        Assertions.assertEquals(-1L, result);

        // 用户名长度
        user.setUserAccount("qh");
        user.setUserPassword("123456");
        user.setCheckPassword("123456");
        result = userService.register(user.getUserAccount(),
                user.getUserPassword(),
                user.getCheckPassword(),
                user.getInvitationCode());
        Assertions.assertEquals(-1L, result);

        // 密码长度
        user.setUserAccount("IamQhe");
        user.setUserPassword("12345");
        user.setCheckPassword("12345");
        result = userService.register(user.getUserAccount(),
                user.getUserPassword(),
                user.getCheckPassword(),
                user.getInvitationCode());
        Assertions.assertEquals(-1L, result);

        // 密码和校验密码一致
        user.setUserAccount("IamQhe");
        user.setUserPassword("123456");
        user.setCheckPassword("123457");
        result = userService.register(user.getUserAccount(),
                user.getUserPassword(),
                user.getCheckPassword(),
                user.getInvitationCode());
        Assertions.assertEquals(-1L, result);

        // 账号名重复
        user.setUserAccount("qhe");
        user.setUserPassword("123456");
        user.setCheckPassword("123456");
        result = userService.register(user.getUserAccount(),
                user.getUserPassword(),
                user.getCheckPassword(),
                user.getInvitationCode());
        Assertions.assertEquals(-1L, result);

        // todo 邀请码


        // 注册成功
        user.setUserAccount("admin");
        user.setUserPassword("123456");
        user.setCheckPassword("123456");
        result = userService.register(user.getUserAccount(),
                user.getUserPassword(),
                user.getCheckPassword(),
                user.getInvitationCode());
        Assertions.assertNotEquals(-1L, result);
    }

    @Test
    public void loginTest() {

    }

}
