package com.newcoder.wenda.controller;

import com.newcoder.wenda.model.EntityType;
import com.newcoder.wenda.model.HostHolder;
import com.newcoder.wenda.service.LikeService;
import com.newcoder.wenda.util.WendaUtil;
import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by apple on 2017/8/30.
 */
@Controller
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;
    @RequestMapping(value = "like",method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if (hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(value = "dislike",method = RequestMethod.POST)
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId){
        if (hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        long disLikeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(disLikeCount));
    }


}
