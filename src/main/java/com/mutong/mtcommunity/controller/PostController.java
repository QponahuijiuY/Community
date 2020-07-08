package com.mutong.mtcommunity.controller;

import cn.hutool.core.util.ObjectUtil;
import com.mutong.mtcommunity.model.Post;
import com.mutong.mtcommunity.model.User;
import com.mutong.mtcommunity.service.PostService;
import com.mutong.mtcommunity.service.UserService;
import com.mutong.mtcommunity.utils.CommunityConstant;
import com.mutong.mtcommunity.utils.HostHolder;
import com.mutong.mtcommunity.utils.Page;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @description: 文章相关Controller
 * @Author: gengchen.jing@yoyi.com.cn
 * @Date: 2020-06-22 16:16
 */
@Controller
public class PostController implements CommunityConstant {
    @Resource
    private HostHolder hostHolder;

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;
    @GetMapping("/add")
    public String getAddPage(Model model){
        User user = hostHolder.getUser();
        if (ObjectUtil.isNull(user) || ObjectUtil.isEmpty(user)){
            model.addAttribute("loginMsg","你还没有登陆");
            return "redirect:/login";
        }
        return "jie/add";

    }

//    @GetMapping
//    public String editPost(){
//
//    }





    @PostMapping("/add")
    public String add(@RequestParam("title") String title, @RequestParam("description") String content, @RequestParam("quiz") Integer specialColumn, Model model, HttpSession session,@RequestParam("code") String code){
        //从session里面获取验证码
        String kaptcha = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(kaptcha) || !kaptcha.equalsIgnoreCase(code)){
            model.addAttribute("codeMsg","验证码不正确");
            return "jie/add";
        }
        model.addAttribute("title",title);
        model.addAttribute("description",content);
        model.addAttribute("quiz",specialColumn);
//        String defaultDescription = "<p id=\"descriptionP\"></p>";
//        content = content.replaceAll("<p id=\"descriptionP\"></p>", ""); //剔出每次编辑产生的冗余p标签
//        content = content.replaceAll("<p id=\"descriptionP\" class=\"video\"></p>", ""); //剔出每次的冗余p标签
//        content = content.replaceAll("<p class=\"video\" id=\"descriptionP\"></p>", ""); //剔出每次的冗余p标签
//        content = content.replaceAll("qs.niter", "qcdn.niter"); //剔出每次编辑产生的冗余p标签
        title = title.trim();
        User user = hostHolder.getUser();
        Post post = new Post();
        post.setUserId(user.getId());
        post.setSpecialColumn(specialColumn);
        post.setContent(content);
        post.setTitle(title);
        post.setCreateTime(new Date());
        post.setModTime(new Date());
        post.setPageView(0);
        post.setCommentCount(0);
        post.setType(0);
        post.setStatus(1);
        post.setScore(DEFAULT_SCORE);
        post.setLikeCount(0);
        postService.insertPost(post);

        return "redirect:/";
    }

    @Transactional
    @GetMapping("/detail/{postId}")
    public String getPost(@PathVariable("postId") int postId, Model model, Page page){
        //访问量加一
        postService.increasePageView(postId);
        //获取帖子信息
        Post post = postService.findPostById(postId);
//        String content = post.getContent();
//        content = content.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
//        content = content.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
//        content = content.replaceAll("&nbsp;", "");//去除&nbsp;
//        String content1 = EscapeUtil.unescape(content);
//        model.addAttribute("content",content1);
        model.addAttribute("post",post);
        //获取作者信息
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user",user);
        return "jie/detail";
    }

//    public static void main(String[] args) {
//        Html4Escape html4Escape = new Html4Escape();
//        String a = "<p id=\"descriptionP\"></p><h1><b><font color=\"#c24f4a\"><u><strike>test</strike></u></font></b></h1>";
//        //5Lym5a625piv5LiA5Liq6Z2e5bi46ZW/55qE5a2X56ym5Liy
//        String escape = EscapeUtil.escape(a);
//        System.out.println(escape);
//
//        String unescape = EscapeUtil.unescape(escape);
//        System.out.println(unescape);
//
//    }
}
