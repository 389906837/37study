package com.cloud.cang.pay.wechat.util.httpclient;

import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;

public interface Validator {
  boolean validate(CloseableHttpResponse response) throws IOException;
}
