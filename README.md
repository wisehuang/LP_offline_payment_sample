# LP_offline_payment_sample

## How to use

1. build project to jar
2. Leverage [OneTimeKey generator](https://sandbox-web-pay.line.me/web/sandbox/payment/oneTimeKey?countryCode=TW&paymentMethod=card&point=20)

### payment
$ java -jar OfflinePayment_Sample.jar payment {oneTimeKey}

### check
$ java -jar OfflinePayment_Sample.jar check {orderId}
