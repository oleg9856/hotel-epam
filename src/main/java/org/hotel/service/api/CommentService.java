package org.hotel.service.api;

import org.hotel.entity.user.Comment;
import org.hotel.exception.ServiceException;

import java.util.List;

public interface CommentService {

    void saveOrUpdateComment(Comment comment) throws ServiceException;
    void deleteById(Integer id) throws ServiceException;
    List<Comment> getAllComments(int lim1, int lim2) throws ServiceException;

}
