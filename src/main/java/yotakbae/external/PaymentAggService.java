
package yotakbae.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="paymentcontext", url="${api.url.payment}")
// @FeignClient(name="PaymentContext", url="http://localhost:8082")
public interface PaymentAggService {

    @RequestMapping(method= RequestMethod.POST, path="/paymentAggs")
    public void dopay(@RequestBody PaymentAgg paymentAgg);

}
