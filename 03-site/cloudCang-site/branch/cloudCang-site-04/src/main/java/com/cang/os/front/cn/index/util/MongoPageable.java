package com.cang.os.front.cn.index.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by YLF on 2019/8/29.
 */
public class MongoPageable implements Serializable, Pageable {
        private static final long serialVersionUID = 1L;

        // 当前页
        private Integer pagenumber = 1;
        // 当前页面条数
        private Integer pagesize = 10;
        // 排序条件
        private Sort sort;

        public MongoPageable(Integer pagenumber, Integer pagesize, Sort sort) {
                this.pagenumber = pagenumber;
                this.pagesize = pagesize;
                this.sort = sort;
        }

        @Override
        public Pageable first() {
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public int getOffset() {
                // TODO Auto-generated method stub
                return (pagenumber - 1) * pagesize;
        }

        @Override
        public int getPageNumber() {
                // TODO Auto-generated method stub
                return pagenumber;
        }

        @Override
        public int getPageSize() {
                // TODO Auto-generated method stub
                return pagesize;
        }


        @Override
        public boolean hasPrevious() {
                // TODO Auto-generated method stub
                return false;
        }

        @Override
        public Pageable next() {
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public Pageable previousOrFirst() {
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public Sort getSort() {
                // TODO Auto-generated method stub
                return sort;
        }

        }