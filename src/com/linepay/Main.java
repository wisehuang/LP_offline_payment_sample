package com.linepay;

import com.linepay.offline.OfflinePayment;
import org.apache.http.HttpResponse;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String api = args[0];
        String parameter = args[1];
        String response = null;

        try {
            OfflinePayment offlinePayment = new OfflinePayment();

            if("payment".equals(api)) {
                response = offlinePayment.payment(parameter);

            } else if ("check".equals(api)) {
                response = offlinePayment.check(parameter);
            }

            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
