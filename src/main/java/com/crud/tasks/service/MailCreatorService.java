package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.counter.TaskCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    private TaskCounter taskCounter;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<> ();
        functionality.add ("You can manage your tasks");
        functionality.add ("Provides connection with Trello Acount");
        functionality.add ("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable ("tasks_url", "http://localhost:8888/task-app/");
        context.setVariable ("button", "Visit website");
        context.setVariable ("company_name", companyConfig.getCompanyName ());
        context.setVariable ("company_goal", companyConfig.getCompanyGoal ());
        context.setVariable ("company_mail", companyConfig.getCompanyMail ());
        context.setVariable ("show_button", true);
        context.setVariable ("is_friend", true);
        context.setVariable ("admin_name", "Amadeusz");
        context.setVariable ("admin_config", adminConfig);
        context.setVariable ("application_functionality", functionality);
        context.setVariable ("final_message", taskCounter.countTasks ());
        return templateEngine.process ("mail/created-trello-card-mail", context);
    }
}
