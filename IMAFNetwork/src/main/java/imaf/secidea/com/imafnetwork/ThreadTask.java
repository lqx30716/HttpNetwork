/*
 * File Name：ThreadTask.java
 * Copyright：Copyright 2008-2015 CiWong.Inc. All Rights Reserved.
 * Description： ThreadTask.java
 * Modify By：res-jxliu
 * Modify Date：2015年3月21日
 * Modify Type：Add
 */
package imaf.secidea.com.imafnetwork;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程任务类,用来管理程序中出现的所有线程
 */
public class ThreadTask
{
    /**
     * 线程任务的实例
     */
    private static ThreadTask instance;

    /**
     * 网络线程最大数量
     */
    private final int netThreadCount = 5;


    /**
     * 网络线程池
     */
    private ThreadPoolExecutor netThreadPool;



    /**
     * 网络线程队列
     */
    private PriorityBlockingQueue netThreadQueue;



    /**
     * 任务比较
     */
    private Comparator<ImafRequest> taskCompare;

    private ThreadTask()
    {
        taskCompare = new TaskCompare();
        netThreadQueue = new PriorityBlockingQueue<ImafRequest>(netThreadCount,taskCompare);
        netThreadPool = new ThreadPoolExecutor(netThreadCount, netThreadCount,
                0L, TimeUnit.MILLISECONDS, netThreadQueue);

    }
    /**
     * 任务比较器
     *
     * @author RES-KUNZHU
     *
     */
    public class TaskCompare implements Comparator<ImafRequest>
    {

        @Override
        public int compare(ImafRequest lhs, ImafRequest rhs)
        {
            return rhs.getPriori() - lhs.getPriori();
        }

    }
    /**
     * 获取线程管理实例
     * 
     * @return 线程管理实例
     */
    public static ThreadTask getInstance()
    {
        if (instance == null)
        {
            instance = new ThreadTask();
        }
        return instance;
    }

    /**
     * 获取网络线程池
     * @return
     */
    public ThreadPoolExecutor getNetThreadPool()
    {
        return netThreadPool;
    }

    /**
     * 执行网络线程
     *
     * @param task
     *            需要执行的任务
     * @param priority
     */
    public void executorNetThread(Runnable task, int priority)
    {

        if (!netThreadPool.isShutdown())
        {
            netThreadPool.execute(new PrioriTask(priority, task));
        }
    }



}
