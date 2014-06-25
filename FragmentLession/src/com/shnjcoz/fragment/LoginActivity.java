package com.shnjcoz.fragment;

import java.util.Arrays;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.shnjcoz.fragment.common.Callback;
import com.shnjcoz.fragment.common.HttpTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends Activity implements Callback{

	private String fbid = "";
	private static final String TAG = "LoginActivity";
	private ProgressDialog dialog;
	private LoginButton authButton;
	Handler handler;
	private RequestQueue mQueue;
	
	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
				updateView();
	    }

	};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		authButton = (LoginButton)findViewById(R.id.authButton);
		//Log.i(TAG,"authButton.toString: " + authButton.toString());
		
		//dialogの表示
		
		Session session = Session.getActiveSession();
		if(session == null){
			if(savedInstanceState != null){
				session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
			}
			
			if(session == null){
				session = new Session(this);
			}
			
			Session.setActiveSession(session);
			if(session.getState().equals(SessionState.CREATED_TOKEN_LOADED)){
				session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));;
			}
		}
		updateView();
	}

	private void updateView() {
		Session session = Session.getActiveSession();
		Log.i(TAG, "updateView...desu");
		if(session.isOpened()){
			Log.i(TAG, "updateView/sessionOpen...desu");
			//FBIDの取得
			makeMeRequest(session);
			Log.i(TAG, "FBID: " + fbid);
			//ServerSideへの確認
			//while (fbid == ""){
			//	sleep(1000);
			//	Log.i(TAG, "FBIDを待ちます: " + fbid);
			//}
			if(checkUser()){
				Log.i(TAG, "Userは存在しました" );			
				//OK→HomeActivityへ
                //Intent intent = new Intent(IndexActivity.this, HomeActivity.class);
                //startActivity(intent);
			}else{
				//NG→画面表示
				onLogout();
				viewLogin();
			}
			
		}else{
			//画面を表示
			viewLogin();
		}		
	}
	
	private void makeMeRequest(Session session){
		dialog = new ProgressDialog(LoginActivity.this);  
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
        dialog.setMessage("登録中");  
        dialog.show();
		
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {				
			@Override
			public void onCompleted(GraphUser user, Response response) {
				Log.i(TAG,"onCompleted...desu");
				if(user != null){
					fbid = user.getId();
				}
				String url = "http://10.0.2.2:9000/get?id=" + fbid;
                mQueue = Volley.newRequestQueue(LoginActivity.this);
                mQueue.add(new JsonObjectRequest(Method.GET, url, null,
                  new Listener<JSONObject>() {
                      @Override
                      public void onResponse(JSONObject response) {
                          // JSONObjectのパース、List、Viewへの追加等
              			  try {
							String responseCode = response.getJSONArray("Status").getJSONObject(0).getString("status");
							Log.i(TAG,"response Status: " + responseCode);
              			  } catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                      }
                  },
                  new ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          // エラー処理 error.networkResponseで確認
                          // エラー表示など
                      }
                  }));
				final HttpTask httpTask = new HttpTask(LoginActivity.this,"2",fbid);
				httpTask.execute();
				Log.i(TAG, "FBID/onCompleted: " + fbid);			
			}
		});
		request.executeAsync();	
	}
	
	private void viewLogin(){
		Log.i(TAG, "viewLogin...desu");			
		//authButton.setText(R.string.logout_string);
		authButton.setReadPermissions(Arrays.asList("user_location", "user_birthday", "user_likes"));
		authButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onLogin();
			}
		});		
	
	}

	private boolean checkUser() {
		return true;
	}

	private void onLogin() {
		Log.i(TAG, "onLogin...desu");			
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
    		Log.i(TAG, "viewLogin/open close demonai...desu");			
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
    		Log.i(TAG, "viewLogin/open close nodochiraka...desu");			
            Session.openActiveSession(this, true, statusCallback);
        }
	}
    private void onLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

	@Override
	public void callback(String responseCode, String requestCode,
			Map<String, JSONObject> resultMap) {
		
		dialog.dismiss();
		if(responseCode.equals("200")){
            Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
            startActivity(intent);
		}
	}

}
