package com.cansmart.easydldemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String TOKEN_URL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&" +
            "client_id=X9BaqdyxSP0df7V7dfOSOyyG" +
            "&" +
            "client_secret=Qh9a7ySVLoFZ3GQPXs28TgGSgzeW3mTi";
    private Button mBtnChoose;
    private TextView mTvM;
    private TextView mTvK;
    private DecimalFormat df;
    private ImageView mIvImg;
    private ProgressBar mPbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        df = new DecimalFormat("######0.00");
        mBtnChoose = findViewById(R.id.btn_choose);
        mIvImg = findViewById(R.id.iv_img);
        mTvM = findViewById(R.id.tv_result_m);
        mTvK = findViewById(R.id.tv_result_k);
        mPbProgress = findViewById(R.id.pb_progress);
        mBtnChoose.setOnClickListener(v ->
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE));
        // initToken();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos != null && photos.size() > 0) {
                    String s = photos.get(0);
                    Bitmap bitmap = BitmapFactory.decodeFile(s);
                    Glide.with(MainActivity.this).load(s).into(mIvImg);
                    mTvM.setText("");
                    mTvK.setText("");
                    mPbProgress.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(HttpConfig.getInstance().getToken())) {
                        RequestBody requestBody = getRequestBody(bitmap);
                        RetrofitFactory.getInstance().getApiService().getResult(HttpConfig.getInstance().getToken(), requestBody)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(entity -> {
                                            mPbProgress.setVisibility(View.GONE);
                                            mTvM.setText("鼠标相似度:" + df.format(entity.getResults().get(0).getScore() * 100) + "%");
                                            mTvK.setText("键盘相似度:" + df.format(entity.getResults().get(1).getScore() * 100) + "%");
                                        },
                                        throwable ->
                                                Log.e(TAG, throwable.toString()));
                    } else {
                        Toast.makeText(this, "token为空", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private RequestBody getRequestBody(Bitmap bitmap) {
        String base64 = Bitmap2StrByBase64(bitmap);
        Log.d(TAG, base64);
        Map<String, String> map = new HashMap<>();
        map.put("image", base64);
        map.put("top_num", "5");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String bodyStr = gson.toJson(map);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
    }

    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
