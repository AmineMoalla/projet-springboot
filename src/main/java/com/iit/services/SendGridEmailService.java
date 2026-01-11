package com.iit.services;

import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.Method;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendGridEmailService {
    
     @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void sendEmail(String to, String subject, String content) throws Exception {
        // Utiliser une adresse professionnelle liée au domaine
        Email from = new Email("mohamed.amine.moalla@iit.ens.tn", "YFA School");
        Email toEmail = new Email(to);
        Content plainContent = new Content("text/plain", content);
        Content htmlContent = new Content("text/html", "<p>" + content + "</p><br><small>YFA School</small>");
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(plainContent);
        mail.addContent(htmlContent);
        mail.addPersonalization(new com.sendgrid.helpers.mail.objects.Personalization() {{
            addTo(toEmail);
        }});

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if (response.getStatusCode() >= 400) {
                throw new Exception("Erreur d'envoi d'email: " + response.getBody());
            }
        } catch (Exception ex) {
            throw new Exception("Erreur d'envoi d'email: " + ex.getMessage());
        }
    }
}
