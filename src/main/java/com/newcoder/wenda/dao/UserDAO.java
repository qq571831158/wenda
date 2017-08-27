package com.newcoder.wenda.dao;

import com.newcoder.wenda.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by apple on 2017/8/19.
 */
@Mapper
@Repository
public interface UserDAO {

    String TABLE_NAME = "user";

    String INSERT_FIELDS= "name,password,salt,head_url";

    String SELECT_FIELDS = "id,"+INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(",INSERT_FIELDS,")values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
    User selectById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where name=#{name}"})
    User selectByName(String name);

    @Update({"update",TABLE_NAME,"set password = #{password} where id = #{id}"})
    void updataPassowrd(User user);

    @Delete({"delete form",TABLE_NAME,"where id =#{id}"})
    void deleteById(int id);
}
