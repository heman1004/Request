package yotakbae;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="RequestAgg_table")
public class RequestAgg {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long memberId;
    private Long qty;
    private String status="Registered";

    @PostPersist
    public void onPostPersist(){
        Requested requested = new Requested();
        BeanUtils.copyProperties(this, requested);
        requested.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        yotakbae.external.PaymentAgg paymentAgg = new yotakbae.external.PaymentAgg();
        // mappings goes here
        //LJK
        paymentAgg.setRequestId(this.getId());
        paymentAgg.setMemberId(this.getMemberId());
        paymentAgg.setStatus("Paid");
        //LJK


        RequestContextApplication.applicationContext.getBean(yotakbae.external.PaymentAggService.class)
            .dopay(paymentAgg);


    }

    @PreUpdate
    public void onPreUpdate(){
        ReqCanceled reqCanceled = new ReqCanceled();
        BeanUtils.copyProperties(this, reqCanceled);
        reqCanceled.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
