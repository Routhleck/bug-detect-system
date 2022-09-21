package com.demo.dao;

import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    User findByUsernameAndPassword(@Param("username") String username,
                                   @Param("password") String password);

    boolean updatePassword(@Param("username") String username,
                           @Param("password") String password);

    boolean deleteUser(@Param("username") String username);

    boolean insertUser(@Param("username") String username,
                    @Param("password") String password);
}
