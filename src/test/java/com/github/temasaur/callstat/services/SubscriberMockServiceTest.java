package com.github.temasaur.callstat.services;

import com.github.temasaur.callstat.CallStatApplication;
import com.github.temasaur.callstat.services.subscriber.SubscriberMockService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CallStatApplication.class)
public class SubscriberMockServiceTest extends SubscriberServiceTest {
    @Override
    protected void getService() {
        subscriberService = new SubscriberMockService();
    }
}

