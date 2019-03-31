package com.example.disanfangtext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.igexin.sdk.PushManager;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button tojs;
    WebView webView;
    private Button button;
    private Button sao;
    private Button sheng;
    private Button tiao;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
// com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), DemoIntentService.class);
        button = findViewById(R.id.button);
        sao = findViewById(R.id.sao);
        sheng = findViewById(R.id.sheng);
        webView = findViewById(R.id.web_id);
        tojs = findViewById(R.id.calljs_but_id);
        tiao = findViewById(R.id.tiao);

      sheng.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this, ScanActivity.class);
              startActivity(intent);
          }
      });
        tiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Three.class);
                startActivity(intent);
            }
        });

         sao.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                 intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                 intentIntegrator.setCaptureActivity(four.class);

                 intentIntegrator.setPrompt("请扫描二维码");
                 intentIntegrator.setCameraId(0);
                 intentIntegrator.setBarcodeImageEnabled(true);
                 intentIntegrator.initiateScan();

             }
         });


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UMShareAPI umShareAPI = UMShareAPI.get(MainActivity.this);
                    umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }
                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map)
                        {

                        }
                        @Override
                        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        }
                        @Override
                        public void onCancel(SHARE_MEDIA share_media, int i) {
                        }
                    });
                }
            });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new JsToAndroid(),"sssss");
        webView.loadUrl("file:///android_asset/text.html");

        webView.setWebChromeClient(new WebChromeClient());
        tojs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result="你好";
                webView.loadUrl("javascript:returnResult('" + result + "')");
            }
        });

    }

    public  class   JsToAndroid{
      @JavascriptInterface
      public void hello(final String str ){
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  Toast.makeText(MainActivity.this , str , Toast.LENGTH_LONG).show();

                  Intent intent = new Intent(MainActivity.this , TowActivity.class);
                  startActivity(intent);
              }
          });
      }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult !=null)
        {
            String contents = intentResult.getContents();
            Log.e("onActivityResult",contents+"");
            Toast.makeText(MainActivity.this,contents,Toast.LENGTH_SHORT).show();
        }
        Log.e("啦啦啦啦啦啦",intentResult+"");

    }

}
