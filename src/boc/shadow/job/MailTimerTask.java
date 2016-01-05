package boc.shadow.job;

import boc.shadow.dao.ItemDAO;
import boc.shadow.dao.NotifyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.TimerTask;

/**
 * Created by Tong on 2015/8/29.
 */
public class MailTimerTask extends TimerTask{
    private String to;
    private Integer groupId;
    private Integer jobId;
    private String subject;
    private String mailContent;
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    @Qualifier("ItemDAO")
    @Autowired
    private ItemDAO itemDao;
    @Qualifier("NotifyDAO")
    @Autowired
    private NotifyDAO notifyDao;

    public MailTimerTask(String to, Integer groupId, Integer jobId) {
        this.to = to;
        this.groupId = groupId;
        this.jobId = jobId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Override
    public void run() {
        // For test
        System.out.println("Start the job");
        //SchedulerJob schedulerJob = (SchedulerJob) jobExecutionContext.getMergedJobDataMap().get("schedulerJob");
        try {
            itemDao = (ItemDAO) ctx.getBean("ItemDAO");
            notifyDao = (NotifyDAO) ctx.getBean("NotifyDAO");
            //to = schedulerJob.getTo();
            subject = "SHADOW NOTICE: " + itemDao.getItem(groupId).getItem_name();
            mailContent = "You have received this notice! \r\n\r\nSuch Item Description is : \r\n"
                    + itemDao.getItem(groupId).getItem_description();
            mailContent += "\r\n\r\nPlease go to the SHADOW system (http://22.188.12.15/shadow-git/login.php)" +
                    "and check this item~~\r\n\r\nFrom: shadow_admin@boc.com";
            SendNotifyMail sendNotifyMail = new SendNotifyMail(to,subject, mailContent);
            sendNotifyMail.sendMail();

            // delete the data from database if the autoType is 'ONCE'
            if (notifyDao.getNotify(jobId).getAuto_type().toString().equals("ONCE")) {
                notifyDao.deleteNotify(jobId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean cancel() {
        return super.cancel();
    }

    @Override
    public long scheduledExecutionTime() {
        return super.scheduledExecutionTime();
    }
}
