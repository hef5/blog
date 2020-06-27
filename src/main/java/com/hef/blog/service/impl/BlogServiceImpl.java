package com.hef.blog.service.impl;

import com.hef.blog.NotFoundException;
import com.hef.blog.dao.BlogRepository;
import com.hef.blog.entity.Blog;
import com.hef.blog.entity.Tag;
import com.hef.blog.entity.Type;
import com.hef.blog.service.BlogService;
import com.hef.blog.util.MarkdownUtils;
import com.hef.blog.util.MyBeanUtils;
import com.hef.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        Specification<Blog> spec = (root, cq, cb) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                predicates.add(cb.like(root.get("title"), "%"+blog.getTitle()+"%"));
            }
            if (blog.getTypeId() != null) {
                predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
            }
            if (blog.isRecommend()) {
                predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return blogRepository.findAll(spec, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRepository.getOne(id);
        if (blog1 == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll((Specification<Blog>) (root, cq, cb) -> {
            Join join = root.join("tags");
            return cb.equal(join.get("id"),tagId);
        },pageable);
    }


    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

}
