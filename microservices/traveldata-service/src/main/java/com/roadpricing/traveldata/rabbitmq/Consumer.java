package com.roadpricing.traveldata.rabbitmq;

import com.roadpricing.traveldata.Model.TravelData;
import com.roadpricing.traveldata.Service.TravelDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Consumer {
//    @Autowired
//    TravelDataService travelDataService;
//    private CountDownLatch latch = new CountDownLatch(1);
//
//    private Logger logger = LoggerFactory.getLogger(Consumer.class);
//
//    public void receiveMessage(TravelData travelData){
//        logger.info("[🌠] " + "Recieved from queue:"+ travelData + " [🌠]");
//        latch.countDown();
//    }
//    public CountDownLatch getLatch(){
//        return latch;
//    }
}
