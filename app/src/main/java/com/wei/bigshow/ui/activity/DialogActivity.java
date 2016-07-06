package com.wei.bigshow.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wei.bigshow.R;

public class DialogActivity extends AppCompatActivity {

    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        container = (ViewGroup) findViewById(R.id.container);

        View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        container.setOnClickListener(dismissListener);
        container.findViewById(R.id.btn_dialog_close).setOnClickListener(dismissListener);
        Intent data = getIntent();
        final int type = data.getIntExtra("type", 0);
        ((TextView)container.findViewById(R.id.tv_dialog_title)).setText(data.getStringExtra("title"));
        ((TextView)container.findViewById(R.id.et_dialog_content)).setText(data.getStringExtra("content"));
        container.findViewById(R.id.btn_dialog_done).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String content = ((TextView)container.findViewById(R.id.et_dialog_content)).getText().toString();
                Intent intent= new Intent();
                intent.putExtra("result",content);
                setResult(type, intent);
                DialogActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    public void dismiss() {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

}
