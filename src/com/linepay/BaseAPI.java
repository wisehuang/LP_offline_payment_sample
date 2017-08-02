package com.linepay;

import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class BaseAPI {

    protected String baseUrl;
    protected String channelId;
    protected String channelSecret;
    protected String merchantDeviceType;
    protected String merchantDeviceProfileId;
    protected String orderId;
    protected List<Header> headers;

    public BaseAPI() throws IOException {
        Properties prop = getConfig();

        baseUrl = (String)prop.get("URL");
        channelId = (String)prop.get("X-LINE-CHANNELID");
        channelSecret = (String)prop.get("X-LINE-CHANNELSECRET");
        merchantDeviceType = (String)prop.get("X-LINE-MERCHANTDEVICETYPE");
        merchantDeviceProfileId = (String)prop.get("X-LINE-MERCHANTDEVICEPROFILEID");
        orderId = (String)prop.get("ORDER_PREFIX");

        headers = Lists.newArrayList(createHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                createHeader("X-LINE-ChannelId", channelId),
                createHeader("X-LINE-ChannelSecret", channelSecret),
                createHeader("X-LINE-MerchantDeviceType", merchantDeviceType),
                createHeader("X-LINE-MerchantDeviceProfileId", merchantDeviceProfileId));
    }

    private Properties getConfig() throws IOException {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = this.getClass().getClassLoader().getResourceAsStream("config.properties");

            // load a properties file
            prop.load(input);

            return prop;

        } catch (Exception e) {
            throw e;
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected String sendRequest(String method, String url, String payload) throws IOException {
        String response = null;
        ResponseHandler<String> responseHandler=new BasicResponseHandler();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            if("GET".equals(method)) {
                HttpGet getRequest = new HttpGet(url);

                for(Header h: headers) {
                    getRequest.addHeader(h);
                }

                response = httpClient.execute(getRequest, responseHandler);
            } else if("POST".equals(method)) {
                HttpPost postRequest = new HttpPost(url);
                for(Header h:headers) {
                    postRequest.addHeader(h);
                }
                StringEntity entity = new StringEntity(payload);

                postRequest.setEntity(entity);

                response = httpClient.execute(postRequest, responseHandler);
            }

        } catch (IOException e) {
            throw e;
        }

        return response;
    }

    private Header createHeader(String name, String value) {
        return new BasicHeader(name, value);
    }

}
