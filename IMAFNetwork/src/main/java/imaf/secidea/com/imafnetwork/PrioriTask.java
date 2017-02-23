package imaf.secidea.com.imafnetwork;

/**
 * Created by admin on 2017/1/16.
 */

public class PrioriTask implements Runnable {
    protected int priori = ThreadPeriod.NORMAL;


    private Runnable task;

    /**
     *
     * Cnstructor Method。
     *
     * @param priority
     *            优先级
     * @param runnable
     *            线程
     */
    public PrioriTask(int priority, Runnable runnable)
    {
        priori = priority;
        task = runnable;
    }



    @Override
    public void run()
    {
        if (task != null)
        {
            task.run();
        }
    }

}
