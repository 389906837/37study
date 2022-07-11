package com.cloud.cang.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 异常任务执行器
 * @author zhouhong
 * @version 1.0.0
 */
public class ExecutorManager {
	private static ExecutorService exec = null;
	private static Object lock = new Object();
	private static Logger LOGGER = LoggerFactory
			.getLogger(ExecutorManager.class);
	private ExecutorManager() {

	}
	private static ExecutorManager instance = new ExecutorManager();

	public static ExecutorManager getInstance() {
		synchronized (lock) {
			if (exec == null) {
				ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("task-%d").build();
				exec = Executors.newFixedThreadPool(Runtime.getRuntime()
						.availableProcessors() * 5, threadFactory);
			}
		}
		return instance;
	}
	public void execute(Runnable command) {
		execute(command, true);
	}
	public Future execute(Runnable command, boolean isBackGrand) {
		try {
			if (isBackGrand) {
				exec.execute(command);
				return null;
			} else {
				return exec.submit(command);
			}
		} catch (Exception e) {
			LOGGER.error("async execute error", e);
		}
		return null;
	}

	public void shutdown() {
		if (exec != null) {
			exec.shutdownNow();
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> executeTask(TaskAction... tasks) {
		final CountDownLatch latch = new CountDownLatch(tasks.length);
		List<Future<T>> futures = new ArrayList<Future<T>>();
		List<T> resultList = new ArrayList<T>();
		for (final TaskAction<T> runnable : tasks) {
			Future<T> future = exec.submit(new Callable<T>() {
				@Override
				public T call() throws Exception {
					try {
						return runnable.doInAction();
					} finally {
						latch.countDown();
					}

				}
			});
			futures.add(future);
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			LOGGER.error("Executing Task is interrupt.", e);
		}
		for (Future<T> future : futures) {
			try {
				T result = future.get();
				if (result != null) {
					resultList.add(result);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return resultList;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			ExecutorManager.getInstance().execute(new Runnable() {

				@Override
				public void run() {
					LOGGER.debug("info");

				}
			});
		}

	}
}
