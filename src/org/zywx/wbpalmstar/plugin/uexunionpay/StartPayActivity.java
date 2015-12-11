package org.zywx.wbpalmstar.plugin.uexunionpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

public class StartPayActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String tn = intent.getStringExtra(JsConst.INTENT_ORDER_INFO);
        String mode = intent.getStringExtra(JsConst.INTENT_MODE);
        UPPayAssistEx.startPayByJAR(this, PayActivity.class, null, null,
                tn, mode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("djf-onActivityResult", "data:" + data);
        setResult(JsConst.REQUEST_CODE_START_PAY_BY_JAR, data);
        finish();
    }
}
