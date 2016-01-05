package boc.shadow;

import boc.shadow.dao.NotifyDAO;
import boc.shadow.dao.UserDAO;
import boc.shadow.dao.impl.NotifyDaoImpl;
import boc.shadow.dao.impl.UserDaoImpl;
import boc.shadow.domain.User;
import boc.shadow.job.SendNotifyMail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Tong on 2015/8/27.
 */
public class testDemo {

    @Qualifier("sessionFactory")
    @Resource
    private SessionFactory sessionFactory;
    @Qualifier("UserDAO")
    @Resource
    private UserDAO userDao;
    @Qualifier("NotifyDAO")
    @Resource
    private NotifyDAO notifyDao;


    public testDemo() {
    }

    public static void main (String [] args) {
        try {
            new testDemo().displayUserMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUserMail() {
        ApplicationContext ctx = null;
        ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        userDao = (UserDAO) ctx.getBean("UserDAO");
        System.out.println("start to get the user mail!");

        User user = userDao.getUser(5);
        String email = user.getUser_mail();
        System.out.println(email);

        notifyDao = (NotifyDAO) ctx.getBean("NotifyDAO");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(notifyDao.getNotify(18).getAuto_date());
        System.out.println(dateString);

        String[] timeArray = dateString.split(" ");

        //get the year, month , day
        String[] timeArrayFirst = timeArray[0].split("-");
        String year = timeArrayFirst[0];
        String month = timeArrayFirst[1];
        String day = timeArrayFirst[2];
        System.out.println(year+" "+month+" "+day+" "+timeArray[1]);

        //get the hour, minute, second
        String[] timeArraySecond = timeArray[1].split(":");
        String hour = timeArraySecond[0];
        String minute = timeArraySecond[1];
        String second = timeArraySecond[2];


        // get the week
        Calendar c = Calendar.getInstance();
        c.setTime(notifyDao.getNotify(18).getAuto_date());
        String week = Integer.toString(c.get(Calendar.DAY_OF_WEEK));

        System.out.println(year+" "+month+" "+day+" "+hour+" "+minute+" "+second+" "+week);

        SendNotifyMail sendNotifyMail = new SendNotifyMail();
        sendNotifyMail.setTo(email);
        sendNotifyMail.setSubject("hehehehe");
        sendNotifyMail.setMailContent("nothing");
        try {
            sendNotifyMail.sendMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Session getSession () {
        System.out.println(sessionFactory==null?"true1":"false1");
        Session session = sessionFactory.openSession();
        System.out.println(session == null?"true2":"false2");
        return session;
    }
}
