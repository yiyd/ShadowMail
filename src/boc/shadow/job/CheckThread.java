package boc.shadow.job;

import boc.shadow.dao.NotifyDAO;
import boc.shadow.dao.UserDAO;
import boc.shadow.domain.Notify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;


/**
 * Created by Tong on 2015/8/18.
 * This class is used to check the current database and current notifyJobList
 * if add => new mailJob and trigger scheduler
 * if delete => destroy existed mailJob
 * if update => delete old , build new
 */
public class CheckThread {
    private Integer autoId;
    private Integer itemId;
    private String autoType;
    private Date autoDate;
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    private static Map<String, SchedulerJob> jobMap = new HashMap<String, SchedulerJob>();
    @Qualifier("NotifyDAO")
    @Autowired
    private NotifyDAO notifyDao;
    @Qualifier("UserDAO")
    @Autowired
    private UserDAO userDao;


    public void run() {
        userDao = (UserDAO) ctx.getBean("UserDAO");
        notifyDao = (NotifyDAO) ctx.getBean("NotifyDAO");
       // schedulerFactoryBean = (SchedulerFactoryBean)ctx.getBean("schedulerFactoryBean");

        try {
            // each second check the database and jobList
            while (true) {
                // get all notifies fr
                List<Notify> notifyList = notifyDao.findAll();

                // get all current jobs
                List<SchedulerJob> jobList = getAllJob();

                for (Notify notify : notifyList) {
                    autoId = notify.getAuto_id();
                    itemId = notify.getItem().getItem_id();
                    autoType = notify.getAuto_type();
                    autoDate = notify.getAuto_date();

                    String currentMailAddress = userDao.getUser(notify.getUser().getUser_id()).getUser_mail();

                    //System.out.println(autoId+" "+itemId+" "+currentCronExpression+" "+currentMailAddress);
                    // if exist
                    if (jobMap.get(itemId + "_" + autoId) != null) {
                        SchedulerJob schedulerJob = jobMap.get(itemId + "_" + autoId);
                        String oldMailAddress = schedulerJob.getTo().toString();
                        boolean flag = false;

                        // check the current notify setting
                        if (!currentMailAddress.equals(oldMailAddress)) {
                            // update the mail address
                            schedulerJob.setTo(currentMailAddress);
                            System.out.println("different address");
                            flag = true;
                        }

                        if (autoDate.after(schedulerJob.getAutoDate())) {
                            schedulerJob.setAutoDate(autoDate);
                            System.out.println(autoDate+" "+schedulerJob.getAutoDate()+" "+"different date");
                            flag = true;
                        }

                        if (!schedulerJob.getAutoType().toString().equals(autoType.toString())) {
                            schedulerJob.setAutoType(autoType);
                            System.out.println(autoType+" "+schedulerJob.getAutoType()+" "+"different autotype");
                            flag = true;
                        }

                        // check if something is updated
                        if (flag) {
                            // cancel the original timer setting
                            schedulerJob.getTimer().cancel();
                            System.out.println("FLAG IS TRUE");
                            // reBuild the Timer
                            Timer timer = new Timer();
                            schedulerJob.setTimer(timer);
                            // add the timerTask
                            switch (autoType) {
                                case "ONCE":
                                    schedulerJob.getTimer().schedule(new MailTimerTask(currentMailAddress, itemId, autoId), autoDate);
                                    // delete the schedulerJob when the task is done
                                    deleteJob(schedulerJob);

                                    System.out.println("UPDATE:  ONCE SCHEDULER");
                                    break;

                                case "DAILY":
                                    schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                            itemId, autoId), autoDate, 24*3600*1000);
                                    break;

                                case "WEEKLY":
                                    schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                            itemId, autoId), autoDate, 7*24*3600*1000);
                                    break;
                                case "MONTHLY":
                                    schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                            itemId, autoId), autoDate, 30*24*3600*1000);
                                    break;

                                case "QUARTERLY":
                                    schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                            itemId, autoId), autoDate, 3*30*24*3600*1000);
                                    break;

                                case "YEARLY":
                                    schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                            itemId, autoId), autoDate, 365*24*3600*1000);
                                    break;

                                default:
                                    break;
                            }
                            //schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress, itemId), autoDate, 10000);
                        }

                    } else {
                        // if none, new one job
                        //SchedulerJob schedulerJob = new SchedulerJob(autoId, itemId, currentCronExpression, currentMailAddress);
                        SchedulerJob schedulerJob = new SchedulerJob(autoId, itemId, currentMailAddress, autoType, autoDate);
                        addJob(schedulerJob);// add the new job to jobMap
                        System.out.println("NONE");
                        // set the timer
                        Timer timer = new Timer();
                        schedulerJob.setTimer(timer);

                        // add the timerTask
                        switch (autoType) {
                            case "ONCE":
                                schedulerJob.getTimer().schedule(new MailTimerTask(currentMailAddress, itemId, autoId), autoDate);
                                // delete the schedulerJob when the task is done
                                deleteJob(schedulerJob);

                                System.out.println("ADD    ONCE SCHEDULER");
                                break;

                            case "DAILY":
                                schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                        itemId, autoId), autoDate, 24*3600*1000);
                                break;

                            case "WEEKLY":
                                schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                        itemId, autoId), autoDate, 7*24*3600*1000);
                                break;
                            case "MONTHLY":
                                schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                        itemId, autoId), autoDate, 30*24*3600*1000);
                                break;

                            case "QUARTERLY":
                                schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                        itemId, autoId), autoDate, 3*30*24*3600*1000);
                                break;

                            case "YEARLY":
                                schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress,
                                        itemId, autoId), autoDate, 365*24*3600*1000);
                                break;

                            default:
                                break;
                        }
                        //schedulerJob.getTimer().scheduleAtFixedRate(new MailTimerTask(currentMailAddress, itemId), autoDate, 10000);
                    }
                }

                // check the jobList if the job still exist in the database
                for (SchedulerJob schedulerJob : jobList) {
                    System.out.println("check the job list");
                    if (notifyDao.getNotify(schedulerJob.getJobId()) == null) {
                        // delete the scheduler
                        schedulerJob.getTimer().cancel();
                        // delete the job from jobMap
                        deleteJob(schedulerJob);
                        //continue;
                    }
                }

                // check the database each minute!
                Thread.sleep(60*1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<SchedulerJob> getAllJob() {
        List jobList = new ArrayList(jobMap.size());

        for (Map.Entry entry : jobMap.entrySet()) {
            jobList.add(entry.getValue());
        }

        return jobList;
    }

    public static void addJob(SchedulerJob schedulerJob) {
        jobMap.put(schedulerJob.getJobGroupId()+"_"+schedulerJob.getJobId(), schedulerJob);
    }

    public static void deleteJob(SchedulerJob schedulerJob) {
        jobMap.remove(schedulerJob.getJobGroupId()+"_"+schedulerJob.getJobId(), schedulerJob);
    }
}
