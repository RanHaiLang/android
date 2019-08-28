package com.jufanshare.testPlugin;

import android.app.Application;
import android.content.Intent;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK;
import ai.advance.liveness.lib.Market;
import ai.advance.liveness.sdk.activity.LivenessActivity;
import ai.advance.liveness.sdk.fragment.LivenessFragment;

/**
 * This class echoes a string called from JavaScript.
 */
public class TestPlugin extends CordovaPlugin {

  static final int REQUEST_CODE_LIVENESS = 1000;
  static final int REQUEST_CODE_RESULT_PAGE = 1001;
  private static final int PERMISSIONS_REQUEST_CODE = 1;


  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    /*Application application = new Application();
    GuardianLivenessDetectionSDK.init(application,Market.Vietnam);*/
    GuardianLivenessDetectionSDK.init(this.cordova.getActivity().getApplication(), "40f54152d98b93dc", "e657d5387e05cebb",Market.Vietnam);
  }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        //.init(this, "40f54152d98b93dc", "e657d5387e05cebb", "vn");
        if (action.equals("coolMethod")) {
              //通过Intent绑定将要调用的Activity
               Intent intent=new Intent(this.cordova.getActivity(),LivenessActivity.class);
              //加入将要传输到activity中的参数
              intent.putExtra("province", args.getString(0));

              //启动activity
              //this.cordova.startActivityForResult(this, intent, REQUEST_CODE_LIVENESS);


          intent.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());
          //启动Activity
          this.cordova.startActivityForResult((CordovaPlugin) this, intent, REQUEST_CODE_LIVENESS);
          //保持cordova的Activity生命周期，用于随时返回结果到js端代码回调
          this.cordova.setActivityResultCallback(this);


            /*String message = args.getString(0);
            this.coolMethod(message, callbackContext);*/
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
