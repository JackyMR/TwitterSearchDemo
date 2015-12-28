package com.yuan.twittersearchdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.yuan.twittersearchdemo.ui.DividerItemDecoration;
import com.yuan.twittersearchdemo.utils.CommonUtil;
import com.yuan.twittersearchdemo.adapter.SearchAdapter;
import com.yuan.twittersearchdemo.api.API;
import com.yuan.twittersearchdemo.model.Status;
import com.yuan.twittersearchdemo.utils.Log;
import com.yuan.twittersearchdemo.utils.SpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;

    private RecyclerView mRecyclerView;

    private View progressview;

    private SearchAsync searchAsync;

    private SearchAdapter adapter;

    private List<Status> mData = new ArrayList<>();

    private SpUtil spUtil;

    private String info_location;

    private LocationManager locationManager;

    /**
     * 用于监听输入变化并配合hanlder请求网络
     */
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (searchAsync != null) {
                searchAsync.cancel(true);
            }

            int len = s.toString().length();
            if (len > 0) {
                Message msg = Message.obtain();
                msg.what = len;
                hanlder.sendMessageDelayed(msg, 500);
            } else {
                mData.clear();
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private Handler hanlder = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == editText.getText().toString().length()) {
                excuteAsync(editText.getText().toString());
            }
        }

    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i("location = onLocationChanged " + location.toString());
            if (!(location.getLatitude() == 0 && location.getLongitude() == 0)) {
                info_location = location.getLatitude() + "," + location.getLongitude() + "," + location.getAccuracy() + "m";
            }
            if (checkLocationPermission()) {
                locationManager.removeUpdates(locationListener);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //Log.i("location = onStatusChanged " + provider + "/" + status + "/" + extras.toString());
        }

        @Override
        public void onProviderEnabled(String provider) {
            //Log.i("location = onProviderEnabled " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            //Log.i("location = onProviderEnabled " + provider);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        spUtil = SpUtil.getInstance(this);

        //获取oauthtoken
        if (TextUtils.isEmpty(spUtil.getString(SpUtil.ACCESS_TOKEN))) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String token = API.oauth2token();
                        if (!TextUtils.isEmpty(token)) {
                            spUtil.put(SpUtil.ACCESS_TOKEN, token);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(editText, "oauth failed", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }

        initLocation();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.edit_text_search);
        editText.addTextChangedListener(watcher);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (editText.getText().toString().length() > 0) {
                        excuteAsync(editText.getText().toString());
                        CommonUtil.hideInput(MainActivity.this);
                    }
                    return true;
                }
                return false;
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter = new SearchAdapter(this, mData));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommonUtil.hideInput(MainActivity.this);
                return false;
            }
        });

        progressview = findViewById(R.id.lay_loading);
    }


    private void initLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (checkLocationPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    /**
     * Android 6.0 需要验证权限
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            return (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    private void excuteAsync(String content) {
        searchAsync = new SearchAsync();
        searchAsync.execute(content, info_location, CommonUtil.getLocalLang());
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 使用AsyncTask请求数据
     */
    class SearchAsync extends AsyncTask<String, Integer, List<Status>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressview.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        @Override
        protected List<com.yuan.twittersearchdemo.model.Status> doInBackground(String... params) {
            try {
                return API.search(spUtil.getString(SpUtil.ACCESS_TOKEN), params[0], params[1], params[2], null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<com.yuan.twittersearchdemo.model.Status> data) {
            super.onPostExecute(data);
            progressview.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            if (data != null) {
                mData.clear();
                mData.addAll(data);
                adapter.notifyDataSetChanged();
                if (mData.isEmpty()) {
                    Snackbar.make(editText, "none", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(editText, "none", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressview.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            //Snackbar.make(editText, "task canceled", Snackbar.LENGTH_LONG).show();
        }

    }


    /*
    private void test() {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bis = new BufferedInputStream(this.getAssets().open("txt.txt"));
            byte[] buffer = new byte[1024 * 8];
            int len;
            while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            SearchEntity entity = new SearchEntity(new JSONObject(new String(baos.toByteArray())));
            Log.i("entity = " + entity.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    */
}
