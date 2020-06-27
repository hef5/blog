package com.hef.blog.service.impl;

import com.hef.blog.NotFoundException;
import com.hef.blog.dao.TagRepository;
import com.hef.blog.entity.Tag;
import com.hef.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        // 1, 2, 3
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }


    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1 = tagRepository.getOne(id);
        if (tag1 == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, tag1);
        return tagRepository.save(tag1);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    private List<Long> convertToList(String ids){

        List<Long> list = new ArrayList<>();

        if(!"".equals(ids) && ids!=null){
            String[] idArray = ids.split(",");
            for (String s : idArray) {
                if (isInteger(s)) {
                    list.add(Long.valueOf(s));
                } else {
                    Tag tag = new Tag();
                    tag.setName(s);
                    tag = this.saveTag(tag);
                    list.add(tag.getId());
                }
            }
        }
        return list;
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
