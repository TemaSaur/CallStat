package com.github.temasaur.callstat.services;

import com.github.temasaur.callstat.CallStatApplication;
import com.github.temasaur.callstat.repository.SubscriberRepository;
import com.github.temasaur.callstat.services.subscriber.SubscriberImplService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=CallStatApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SubscriberImplServiceTest extends SubscriberServiceTest {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    protected void getService() {
        subscriberService = new SubscriberImplService(subscriberRepository);
    }
}

