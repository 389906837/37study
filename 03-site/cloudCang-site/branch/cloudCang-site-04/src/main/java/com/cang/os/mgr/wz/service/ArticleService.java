package com.cang.os.mgr.wz.service;

import com.cang.os.bean.wz.Article;
import com.cang.os.common.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService extends BaseService<Article> {

    String upload(MultipartFile imgFile, String navicationId);

    void save(MultipartFile titleImageFile, MultipartFile titleImageFileIndex, Article article, String scontent);

     Page<Article> paginationQuery(Integer pageNum, String query);
}