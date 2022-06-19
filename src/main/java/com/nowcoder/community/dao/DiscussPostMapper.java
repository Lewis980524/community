package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper //打上这个注解，才能被容器识别、装配
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

//必须用@Param的情况：参数成为了mysql语句中的组成部分，此时参数需要拥有对应的参数名（参考收藏夹）
    int selectDiscussPostRows(@Param("userId") int userId);

}
