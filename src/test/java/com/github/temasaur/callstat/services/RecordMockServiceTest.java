package com.github.temasaur.callstat.services;

import com.github.temasaur.callstat.CallStatApplication;
import com.github.temasaur.callstat.services.record.RecordMockService;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CallStatApplication.class)
public class RecordMockServiceTest extends RecordServiceTest {
    @Autowired
    private RecordMockService recordMockService;

    @Autowired
    private SubscriberService _subscriberService;

    protected void getService() {
        recordService = recordMockService;
        subscriberService = _subscriberService;
    }
}

