package com.billing.runnable;

import android.os.Handler;
import android.os.Message;

import com.billing.main.Logs;
import com.billing.main.Util;
import com.billing.net.OkHttpClientRequest;

import java.io.UnsupportedEncodingException;

import okhttp3.FormBody;

public class BuyGoodsRunnable implements Runnable {
    private Handler mHandler;
    private String httpUrl;
    private String req;

    public BuyGoodsRunnable(String httpUrl, Handler mHandler, String req) {
        this.mHandler = mHandler;
        this.httpUrl = httpUrl;
        this.req = req;
    }

    @Override
    public void run() {
        String result = OkHttpClientRequest.get().SentRequest(httpUrl+"gwOrder.do", getCommit(req));
        try {
            String resultString = new String(Util.b64Decode(result.toString()),
                    "GBK");
            Logs.logE("resultString", resultString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Message msg = mHandler.obtainMessage();
        msg.what = 6;
        msg.obj = result;
        mHandler.sendMessage(msg);
    }

    private FormBody getCommit(String json) {

        FormBody formBody = new FormBody
                .Builder()
                .add("requestParams", json)
                .build();
        return formBody;
    }

}

