package com.cloud.cang.pay.wechat.util.httpclient;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.util.EntityUtils;

public class SignatureExec implements ClientExecChain {
  final ClientExecChain mainExec;
  final Credentials credentials;
  final Validator validator;

  SignatureExec(Credentials credentials, Validator validator, ClientExecChain mainExec) {
    this.credentials = credentials;
    this.validator = validator;
    this.mainExec = mainExec;
  }

  protected HttpEntity newRepeatableEntity(HttpEntity entity) throws IOException {
    byte[] content = EntityUtils.toByteArray(entity);
    ByteArrayEntity newEntity = new ByteArrayEntity(content);
    newEntity.setContentEncoding(entity.getContentEncoding());
    newEntity.setContentType(entity.getContentType());

    return newEntity;
  }

  protected void convertToRepeatableResponseEntity(CloseableHttpResponse response) throws IOException {
    HttpEntity entity = response.getEntity();
    if (entity != null && !entity.isRepeatable()) {
      response.setEntity(newRepeatableEntity(entity));
    }
  }

  protected void convertToRepeatableRequestEntity(HttpUriRequest request) throws IOException {
    if (request instanceof HttpEntityEnclosingRequestBase) {
      HttpEntity entity = ((HttpEntityEnclosingRequestBase) request).getEntity();
      if (entity != null && !entity.isRepeatable()) {
        ((HttpEntityEnclosingRequestBase) request).setEntity(newRepeatableEntity(entity));
      }
    }
  }

  @Override
  public CloseableHttpResponse execute(HttpRoute route, HttpRequestWrapper request,
      HttpClientContext context, HttpExecutionAware execAware) throws IOException, HttpException {
    if (request.getURI().getHost().endsWith(".mch.weixin.qq.com")) {
      return executeWithSignature(route, request, context, execAware);
    } else {
      return mainExec.execute(route, request, context, execAware);
    }
  }

  private CloseableHttpResponse executeWithSignature(HttpRoute route, HttpRequestWrapper request,
      HttpClientContext context, HttpExecutionAware execAware) throws IOException, HttpException {
    HttpUriRequest newRequest = RequestBuilder.copy(request.getOriginal()).build();
    convertToRepeatableRequestEntity(newRequest);
    // ??????????????????
    newRequest.addHeader("Authorization",
        credentials.getSchema() + " " + credentials.getToken(newRequest));

    // ??????
    CloseableHttpResponse response = mainExec.execute(
        route, HttpRequestWrapper.wrap(newRequest), context, execAware);

    // ?????????????????????
  /*  StatusLine statusLine = response.getStatusLine();
    if (statusLine.getStatusCode() >= 200 && statusLine.getStatusCode() < 300) {
      convertToRepeatableResponseEntity(response);
      if (!validator.validate(response)) {
        throw new HttpException("???????????????????????????????????????");
      }
    }*/
    return response;
  }

}
