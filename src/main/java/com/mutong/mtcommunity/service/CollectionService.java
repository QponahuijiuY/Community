package com.mutong.mtcommunity.service;

import com.mutong.mtcommunity.mapper.PostMapper;
import com.mutong.mtcommunity.utils.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @Author: gengchen.jing@yoyi.com.cn
 * @Date: 2020-07-11 16:02
 */
@Service
public class CollectionService extends RedisKeyUtil {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private PostMapper postMapper;

    /**
     * 收藏 or 未收藏
     * @param userId
     * @param postId
     */
    public void collection(int userId,int postId){
        String collectionUserKey = getCollectionUserKey(userId);
        //true 已经收藏了 false 未收藏
        boolean hasCollection = hasCollection(userId,postId);
        if (hasCollection){
            redisTemplate.opsForSet().remove(collectionUserKey,postId);
        }else {
            //redis key：每一个用户都会有一个对应的key：用来存放它收藏的帖子 id
            // collection:user:userid
            //把postid放到这个set集合里面
            redisTemplate.opsForSet().add(collectionUserKey,postId);
        }
    }

    /**
     * 是否收藏
     * @param userId 用户id
     * @param postId 帖子id
     * @return
     */
    public boolean hasCollection(int userId, int postId){
        String collectionUserKey = RedisKeyUtil.getCollectionUserKey(userId);
        //true post 在 key里面
        //false post 不在 key里面
        return redisTemplate.opsForSet().isMember(collectionUserKey,postId);
    }


}
