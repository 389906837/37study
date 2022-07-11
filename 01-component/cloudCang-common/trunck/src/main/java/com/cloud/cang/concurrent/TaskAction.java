package com.cloud.cang.concurrent;
/**
 * 任务
 * @author zhouhong
 *
 * @param <T>
 */
public interface TaskAction<T> {
	T doInAction();
}