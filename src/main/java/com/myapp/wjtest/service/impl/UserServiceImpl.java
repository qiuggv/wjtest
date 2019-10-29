package com.myapp.wjtest.service.impl;

import com.myapp.wjtest.dao.IUserDao;
import com.myapp.wjtest.entity.User;
import com.myapp.wjtest.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    @Override
    public PageInfo<User> query(String username, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userDao.findByName(username);
        return new PageInfo<User>(list);
    }

    @Override
    public User selectById(Integer id) {
        logger.info("Select user by id: [{}]", id);
        User user = userDao.selectById(id);
        return user;
    }

    @Override
    public int save(User user) {
        userDao.insert(user);
        return user.getId();
    }

    @Override
    public int update(User user) {
        return userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteById(Integer id) {
        return userDao.deleteByPrimaryKey(id);
    }

    /**
    * 事务例子
    *
    * @param list
    * @return
    */
    @Override
    @Transactional
    public int saveSafely(List<User> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        for (User user : list) {
            userDao.insert(user);
        }
        return list.size();
    }
}
