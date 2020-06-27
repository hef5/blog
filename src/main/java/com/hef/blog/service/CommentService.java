package com.hef.blog.service;

import com.hef.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
