package com.doppler.blog.Service;

import com.doppler.blog.GlobalConstants;
import com.doppler.blog.dao.PostDao;
import com.doppler.blog.models.Post;
import com.doppler.blog.models.support.PostFormat;
import com.doppler.blog.utils.DateFormatter;
import com.doppler.blog.utils.Markdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Created by doppler on 2016/5/23.
 */
@Service
public class PostService {
    @Resource
    private PostDao postDao;
    //@Resource
    //private RecentPostsRepository recentPostsRepository;
//    @Resource
//    MongoOperations mongoOperations;
    @Resource
    HashtagService hashtagService;

    private static final String CACHE_POST_ARCHIVE = "post_archive";
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @CacheEvict(value = CACHE_POST_ARCHIVE, allEntries = true)
    public Post createPost(Post post) {
//        if (post.getPostFormat() == PostFormat.MARKDOWN) {
//            post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
//        }
//        post.setCreatedAt(DateFormatter.format(new Date()));
//        post = postRepository.insert(post);
//        logger.info(GlobalConstants.INSERTPOST.value() + post.getTitle());
//        recentPostsRepository.insert(new RecentPosts(post.getId()));
        return post;
    }

    @Cacheable(value = CACHE_POST_ARCHIVE)
    public List<Post> getPublishedPosts(){
        logger.info("not cache,get post archive form db");
        //return postRepository.findAllPostsByStatus(PostStatus.PUBLISHED,new Sort(Sort.Direction.DESC,"_id"));
        return null;
    }

//    public Page<Post> getPublishedPostsByPage(int page, int pageSize){
//        Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC, "_id");
//        return postRepository.findAllPostsByStatusAndPage(PostStatus.PUBLISHED, pageRequest);
//    }

    public Post getById(String postId){

        //return postRepository.findOne(postId);
        return  null;
    }

    public Post getByLink(String postLink){
        //return postRepository.getByLink(postLink);
        return null;
    }

    public List<Post> findAllPosts(){

        return null;
        //return postRepository.findAll(new Sort(Sort.Direction.DESC,"_id"));
    }

    @CacheEvict(value = CACHE_POST_ARCHIVE, allEntries = true)
    public void deletePost(String postId){
        //postRepository.delete(postId);
        logger.info(GlobalConstants.DELETEPOST.value() + postId);
    }
    @CacheEvict(value = CACHE_POST_ARCHIVE, allEntries = true)
    public void updatePost(Post post){
        if (post.getPostFormat() == PostFormat.MARKDOWN)
            post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
        post.setUpdatedAt(DateFormatter.format(new Date()));

//        if(recentPostsRepository.findByPostId(post.getId()) == null)
//        recentPostsRepository.insert(new RecentPosts(post.getId()));
//        mongoOperations.save(post);
        logger.info(GlobalConstants.UPDATEPOST.value() + post.getTitle());
    }
    public List<Post> getRecentPosts(){
        return postDao.findRecentPosts();
    }

    public Set<String> parseHashtagStr(String hashtags_str){
        Set<String> hashtags = new HashSet<String>();
        if(hashtags_str != null && !hashtags_str.isEmpty()){
            String names[] = hashtags_str.split("\\s*,\\s*");
            for(String name : names)
                hashtags.add(hashtagService.findOrCreateByName(name).getName());
        }
        return hashtags;
    }

    public String getHashtags_str(Set<String> hashtags) {
        if (hashtags == null || hashtags.isEmpty())
            return "";
        StringBuilder hashtags_str = new StringBuilder("");
        hashtags.forEach(hashtag -> hashtags_str.append(hashtag).append(","));
        hashtags_str.deleteCharAt(hashtags_str.length() - 1);
        return hashtags_str.toString();
    }

    public List<Post> getPostsByTag(String tagName) {
       return null;
       // return postRepository.getByHashtag(tagName,new Sort(Sort.Direction.DESC,"_id"));
    }
}
