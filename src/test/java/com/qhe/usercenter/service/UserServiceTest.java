package com.qhe.usercenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.qhe.usercenter.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Resource
    UserService userService;

    @Test
    public void saveTest() {
        User user = new User();
        user.setUserAccount("qhe");
        user.setUserPassword("123456");
        user.setUserRole(0);
        boolean save = userService.save(user);
        Assert.isTrue(save, "save error");
    }

    @Test
    public void searchTest() {
        List<User> list = userService.list();
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

}
