package com.cang.os.mgr.wz.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cang.os.bean.wz.Article;
import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.exception.ServiceException;
import com.cang.os.front.cn.index.util.MongoPageable;
import com.cang.os.mgr.wz.dao.ArticleDao;
import com.cang.os.mgr.wz.dao.ArticlecontentDao;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.bean.wz.Navigation;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.common.utils.FtpUser;
import com.cang.os.mgr.wz.dao.NavigationDao;
import com.cang.os.mgr.wz.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort.Order;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.FtpUtils;
import com.cang.os.security.utils.SessionUserUtils;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements
        ArticleService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    ArticleDao articleDao;

    @Autowired
    NavigationDao navigationDao;

    @Autowired
    ArticlecontentDao articlecontentDao;

    @Value("${ftp.ip}")
    private String ip;

    @Value("${ftp.port}")
    private String port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.imagePath}")
    private String ftpImagePath;


    @Override
    public BaseMongoDao<Article> getDao() {
        return articleDao;
    }

    @Override
    public String upload(MultipartFile imgFile, String navicationId) {
        if (StringUtils.isBlank(navicationId)) {
            throw new ServiceException("??????????????????");
        }
        if (imgFile == null) {
            throw new ServiceException("????????????????????????");
        }
        String url = "";
        try {
            Navigation navigation = navigationDao.findById(navicationId);
            String code = navigation.getScode();
            String serverPath = "article/" + DateUtils.dateParseShortString(new Date()) + "/" + code + "/";
            String fileFileName = imgFile.getOriginalFilename();
            fileFileName = fileFileName.substring(fileFileName.lastIndexOf("."));
            String tempName = DateUtils.getCurrentSTimeNumber() + fileFileName;
            FtpUser ftpUser = new FtpUser(ip, port, userName, password);
            if (FtpUtils.uploadToFtpServer(imgFile.getInputStream(), serverPath, tempName, ftpUser)) {
                url = ftpImagePath + "/" + serverPath + tempName;
                return url;
            }
        } catch (IOException e) {
            throw new ServiceException("?????????????????????????????????");
        }
        return null;
    }


    @Override
    public void save(MultipartFile titleImageFile, MultipartFile titleImageFileIndex, Article article,
                     String scontent) {

        if (titleImageFile != null && titleImageFile.getSize() > 0 && titleImageFileIndex != null && titleImageFileIndex.getSize() > 0) {
            // 1.????????????
            String url = upload(titleImageFile, article.getSnavigationId());
            String urlIndex = upload(titleImageFileIndex, article.getSnavigationId());
            article.setStitleImage(url);
            article.setStitleImageIndex(urlIndex);

        } else {
            // ???????????????????????????????????????????????????????????? ?????????????????????
            if (titleImageFile != null && titleImageFile.getSize() > 0 && StringUtils.isNoneBlank(article.getId())) {
                Article oldArticle = articleDao.findById(article.getId());
                String url = upload(titleImageFile, oldArticle.getSnavigationId());
                article.setStitleImage(url);
                article.setStitleImageIndex(oldArticle.getStitleImageIndex());
                article.setIstatus(oldArticle.getIstatus());
            }
            if (titleImageFileIndex != null && titleImageFileIndex.getSize() > 0 && StringUtils.isNoneBlank(article.getId())) {
                Article oldArticle = articleDao.findById(article.getId());
                String url = upload(titleImageFileIndex, oldArticle.getSnavigationId());
                article.setStitleImageIndex(url);
                article.setStitleImage(oldArticle.getStitleImage());
                article.setIstatus(oldArticle.getIstatus());
            }

            if (titleImageFile != null && titleImageFile.getSize() > 0 && titleImageFileIndex != null && titleImageFileIndex.getSize() > 0 && StringUtils.isNoneBlank(article.getId())) {
                String url = upload(titleImageFile, article.getSnavigationId());
                String urlIndex = upload(titleImageFileIndex, article.getSnavigationId());
                article.setStitleImage(url);
                article.setStitleImageIndex(urlIndex);
            }
                /* if (StringUtils.isNoneBlank(article.getId())){
                Article oldArticle=articleDao.findById(article.getId());
                article.setStitleImage(oldArticle.getStitleImage());
                article.setIstatus(oldArticle.getIstatus());
            }*/
        }

        // ??????id??????????????????
        if (StringUtils.isBlank(article.getId())) {
            article.setTaddTime(DateUtils.getCurrentDateTime());
            article.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            article.setTupdateTime(DateUtils.getCurrentDateTime());
            article.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            article.setIdelFlag(0);
            article.setIstatus(10);
            article.setId(null);
            this.insert(article);
            //??????
            Articlecontent articlecontent = new Articlecontent();
            articlecontent.setId(null);
            articlecontent.setSarticleId(article.getId());
            articlecontent.setScontent(scontent);
            articlecontent.setId(null);
            articlecontentDao.insert(articlecontent);

        } else {
            // ??????
            article.setTupdateTime(DateUtils.getCurrentDateTime());
            article.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            article.setIdelFlag(0);
            articleDao.update(article);

            //??????????????????

            Query query = new Query(Criteria.where("sarticleId").is(article.getId()));
            List<Articlecontent> articlecontentList = articlecontentDao.find(query);
            if (articlecontentList != null && articlecontentList.size() > 0) {
                Articlecontent updateArticle = articlecontentList.get(0);
                updateArticle.setScontent(scontent);
                articlecontentDao.update(updateArticle);
            } else {
                Articlecontent articlecontent = new Articlecontent();
                articlecontent.setScontent(scontent);
                articlecontent.setSarticleId(article.getId());
                articlecontent.setId(null);
                articlecontentDao.insert(articlecontent);
            }

        }
    }

    @Override
    public Page<Article> paginationQuery(Integer pageNum, String navigationId) {

        Query query = new Query(Criteria.where("snavigationId").is(navigationId));
        query.addCriteria(Criteria.where("istatus").is(20));
        //articleQuery.with(new Sort(Sort.Direction.DESC, "isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        Sort sort = new Sort(Sort.Direction.DESC, "isort", "taddTime");
        //??????????????????????????????????????????
        MongoPageable pageable = new MongoPageable(pageNum, 5, sort);
        // ????????????????????????
        Long count = mongoTemplate.count(query, Article.class);
        // ??????
        List<Article> list = mongoTemplate.find(query.with(pageable), Article.class);
        // ??????????????????????????????
        Page<Article> pagelist = new PageImpl<>(list, pageable, count);

        return pagelist;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFtpImagePath() {
        return ftpImagePath;
    }

    public void setFtpImagePath(String ftpImagePath) {
        this.ftpImagePath = ftpImagePath;
    }


}