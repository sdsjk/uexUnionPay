package org.zywx.wbpalmstar.plugin.uexunionpay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import org.zywx.wbpalmstar.engine.DataHelper;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.plugin.uexunionpay.vo.PayDataVO;
import org.zywx.wbpalmstar.plugin.uexunionpay.vo.ResultPayVO;

public class EUExUnionPay extends EUExBase {

    private static final String BUNDLE_DATA = "data";
    private int mCallbackId = -1;

    public EUExUnionPay(Context context, EBrowserView eBrowserView) {
        super(context, eBrowserView);
    }

    @Override
    protected boolean clean() {
        return false;
    }


    public void startPay(String[] params) {
        String json = params[0];
        PayDataVO dataVO = DataHelper.gson.fromJson(json, PayDataVO.class);
        if (TextUtils.isEmpty(dataVO.getOrderInfo()) ||
                TextUtils.isEmpty(dataVO.getMode())){
            errorCallback(0,0, "error params");
            return;
        }
        if (params.length>1){
            mCallbackId= Integer.parseInt(params[1]);
        }
        Intent intent = new Intent(mContext, StartPayActivity.class);
        intent.putExtra(JsConst.INTENT_ORDER_INFO, dataVO.getOrderInfo());
        intent.putExtra(JsConst.INTENT_MODE, dataVO.getMode());
        startActivityForResult(intent, JsConst.REQUEST_CODE_START_PAY_BY_JAR);
    }

    private void callBackPluginJs(String methodName, String jsonData){
        String js = SCRIPT_HEADER + "if(" + methodName + "){"
                + methodName + "('" + jsonData + "');}";
        onCallback(js);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ResultPayVO result = new ResultPayVO();
        if( data == null ){
            result.setPayResult(JsConst.RESULT_PAY_UNKNOWN_ERROR);
            return;
        }
        String str =  data.getExtras().getString(JsConst.EXTRA_PAY_RESULT);
        if (!TextUtils.isEmpty(str)){
            if( str.equalsIgnoreCase(JsConst.EXTRA_PAY_RESULT_SUCCESS) ){
                result.setPayResult(JsConst.RESULT_PAY_SUCCESS);//成功
            }else if( str.equalsIgnoreCase(JsConst.EXTRA_PAY_RESULT_FAIL) ){
                result.setPayResult(JsConst.RESULT_PAY_FAIL);//失败
            }else if( str.equalsIgnoreCase(JsConst.EXTRA_PAY_RESULT_CANCEL) ){
                result.setPayResult(JsConst.RESULT_PAY_CANCEL);//取消
            }
        }
        if (mCallbackId!=-1){
            callbackToJs(mCallbackId,false, DataHelper.gson.toJsonTree(result));
        }else{
            callBackPluginJs(JsConst.CALLBACK_START_PAY, DataHelper.gson.toJson(result));
        }
     }
}
