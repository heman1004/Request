package yotakbae;

import yotakbae.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    //LJK
    @Autowired
    RequestAggRepository requestAggRepository;
    //LJK
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCanceled_CancelPol(@Payload PayCanceled payCanceled){

        if(payCanceled.isMe()){
            //LJK
            Optional<RequestAgg> requestOptional = requestAggRepository.findById(payCanceled.getRequestId());
            RequestAgg request = requestOptional.get();
            request.setStatus(payCanceled.getStatus());
            requestAggRepository.save(request);
            //LJK
             //System.out.println("##### listener CancelPol : " + payCanceled.toJson());
        }
    }

}
