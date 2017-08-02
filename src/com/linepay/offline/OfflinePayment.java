package com.linepay.offline;

import com.google.gson.Gson;
import com.linepay.BaseAPI;
import com.linepay.pojo.PaymentBean;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.UUID;

public class OfflinePayment extends BaseAPI {
    public OfflinePayment() throws IOException{
        super();
    }

    public String payment(String oneTimeKey) {
        String paymentUrl = this.baseUrl+ "oneTimeKeys/pay";
        String body = null;

        try {
            PaymentBean b = getPaymentBean(oneTimeKey);
            body = this.sendRequest("POST", paymentUrl, b.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    public String check(String orderId) {
        String checkUrl = this.baseUrl+ "orders/" + orderId + "/check";
        String response = null;

        try {
            response = this.sendRequest("GET", checkUrl, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private PaymentBean getPaymentBean(String oneTimeKey) {
        PaymentBean b = new PaymentBean();
        b.setAmount(100);
        b.setCurrency("TWD");
        b.setOneTimeKey(oneTimeKey);
        b.setOrderId(this.orderId+ UUID.randomUUID().toString());
        b.setProductName("test_product_" + UUID.randomUUID().toString());
        return b;
    }
}
