package com.yuan.twittersearchdemo;

import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuan.twittersearchdemo.Utils.CommonUtil;
import com.yuan.twittersearchdemo.Utils.Log;
import com.yuan.twittersearchdemo.api.API;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private EditText editText;
    private ImageView btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return API.search("android",null,CommonUtil.getLocalLang(MainActivity.this));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(TextUtils.isEmpty(s)) s = "netfail";
                textView.setText(s);
            }
        }.execute();
    }

    private void initView(){
        textView = (TextView) findViewById(R.id.text);
        editText = (EditText) findViewById(R.id.edit_text_search);
        editText.addTextChangedListener(watcher);

        btn_search = (ImageView)findViewById(R.id.iv_bottom_search);
        btn_search.setOnClickListener(this);
        btn_search.setVisibility(View.GONE);

    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if(s.toString().length() > 0){
                btn_search.setVisibility(View.VISIBLE);
            }else{
                btn_search.setVisibility(View.GONE);
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_bottom_search:
                if(editText.getText().toString().length() ==0){
                    editText.setError("请输入内容!");
                }
                break;
        }
    }
}
