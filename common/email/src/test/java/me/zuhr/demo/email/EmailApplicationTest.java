package me.zuhr.demo.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;

/**
 * 启动的话需要将配置文件拷贝过来
 *
 * @author zurun
 * @date 2018/3/13 16:38:29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmailApplication.class)
public class EmailApplicationTest {
    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void sendAttachmentsMail() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("service@zuhr.me");
        helper.setTo("hi@zuhr.me");
        helper.setSubject("主题：有附件");
        helper.setText("邮件内容");
        mailSender.send(mimeMessage);
    }

}