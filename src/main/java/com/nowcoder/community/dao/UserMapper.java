package com.nowcoder.community.dao;


import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectById(int id); //返回查询到的User对象

    User selectByName(String name);

    User selectByEmail(String email);

    int insertUser(User user); //返回插入到mysql里的user对应的主键

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);

    //这些方法需要一个配置文件，配置文件里给每一个方法提供它所需要的sql，然后mybatis在底层会
    //自动地生成一个实现类

}
