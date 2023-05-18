package com.springApplication.moduloProduccion.util.runnable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DefaultCommandLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){

        if (applicationContext.getParent() == null){

            CommandLineRunner runner = applicationContext.getBean(DefaultDataConfig.class).initializeDefaultData();
            try {
                runner.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }
}
