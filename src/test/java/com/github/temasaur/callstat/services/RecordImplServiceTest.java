package com.github.temasaur.callstat.services;

import com.github.temasaur.callstat.CallStatApplication;
import com.github.temasaur.callstat.services.record.RecordImplService;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CallStatApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecordImplServiceTest extends RecordServiceTest {
    @Autowired
    private RecordImplService recordImplService;

    @Autowired
    private SubscriberService _subscriberService;

    protected void getService() {
        recordService = recordImplService;
        subscriberService = _subscriberService;
    }
}

