package com.example.disanfangtext;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;


import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class ScanActivity extends AppCompatActivity {




    private ImageView im;
    String name="帅哥";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_scan);
        im = findViewById(R.id.img);

        createQrcode();

    }

    private void createQrcode() {

        QRtask qRtask = new QRtask(ScanActivity.this, im,name);

    qRtask.execute(name);
 //qRtask.equals(name);
    }
    static  class QRtask extends AsyncTask<String,ImageView,Bitmap> {

           private WeakReference<Context> context;
           private  WeakReference<ImageView>imgview;

        public QRtask(ScanActivity scanActivity, ImageView img, String name) {
                context= new WeakReference<Context>(scanActivity);
                imgview= new WeakReference<>(img);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
             String str =strings[0];
             if (TextUtils.isEmpty(str)){
                 return  null;
             }
             int size=context.get().getResources().getDimensionPixelSize(R.dimen.cc);
         return QRCodeEncoder.syncEncodeQRCode(str,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                imgview.get().setImageBitmap(bitmap);
            }else {
                Toast.makeText(context.get(),"生成失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
