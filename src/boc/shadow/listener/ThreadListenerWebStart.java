package boc.shadow.listener;

import boc.shadow.job.CheckThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Tong on 2015/8/27.
 */
public class ThreadListenerWebStart implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CheckThread checkThread = new CheckThread();
        checkThread.run();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
