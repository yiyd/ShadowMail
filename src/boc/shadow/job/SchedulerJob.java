package boc.shadow.job;

import java.util.Date;
import java.util.Timer;

/**
 * Created by Tong on 2015/8/19.
 */
public class SchedulerJob {
    private Integer jobId;// auto_id
    private Integer jobGroupId; // item_id
    //private String cronExpression; // auto_date+auto_type
    private String to; // email address
    private String autoType;
    private Date autoDate;
    private Timer timer;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public SchedulerJob(Integer jobId, Integer jobGroupId, String to, String autoType, Date autoDate) {
        this.jobId = jobId;
        this.jobGroupId = jobGroupId;
        this.to = to;
        this.autoType = autoType;
        this.autoDate = autoDate;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(Integer jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public String getAutoType() {
        return autoType;
    }

    public void setAutoType(String autoType) {
        this.autoType = autoType;
    }

    public Date getAutoDate() {
        return autoDate;
    }

    public void setAutoDate(Date autoDate) {
        this.autoDate = autoDate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
