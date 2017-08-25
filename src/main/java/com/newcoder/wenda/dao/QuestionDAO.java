package com.newcoder.wenda.dao;

import com.newcoder.wenda.model.Question;
import com.newcoder.wenda.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by apple on 2017/8/19.
 */
@Mapper
@Repository
public interface QuestionDAO {

    String TABLE_NAME = "question";

    String INSERT_FIELDS= "title,content,create_date,user_id,comment_count";

    String SELECT_FIELDS = "id,"+INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(",INSERT_FIELDS,")values (#{title},#{content},#{createDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);



    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);

}
