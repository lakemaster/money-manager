package com.jojobi.mm.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile({"default","test"})
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
