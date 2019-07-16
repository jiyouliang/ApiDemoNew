package com.example.android.apis.mydemo.app;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.apis.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KeystoreSignInfoActivity extends Activity implements View.OnClickListener {

    private Button mBtn;
    private TextView mTvTitle;
    private TextView mTvContent;
    private PackageManager mPkgMgr;
    private static final String TAG = "KeystoreActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keystore_sign_info);
        initView();
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContent = (TextView) findViewById(R.id.tv_content);

        mBtn.setOnClickListener(this);

        mPkgMgr = getPackageManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                getKeystoreSignInfo();
                break;
        }
    }

    private void getKeystoreSignInfo() {
        String md5Signatures = getMD5Signatures(mPkgMgr);
        String sha1Signatures = getSHA1Signatures(mPkgMgr);
        String sha256Signatures = getSHA256Signatures(mPkgMgr);

        mTvContent.setText("md5:\n" + md5Signatures + "\n\n\n" + "sha1:\n" + sha1Signatures + "\n\n\n" + "sha256:\n" + sha256Signatures);

        Log.d(TAG, "md5=" + md5Signatures + ", sha1=" + sha1Signatures + ", sha256=" + sha256Signatures);
    }

    /**
     * 获取md5签名信息
     *
     * @param pm
     */
    private String getMD5Signatures(PackageManager pm) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(signatures[0].toByteArray());
            byte[] digest = md5.digest();

            for (int i = 0; i < digest.length; i++) {
                //转16进制
                if (Integer.toHexString(0xFF & digest[i]).length() == 1) {
                    //1位数，前面补0
                    stringBuffer.append("0").append(Integer.toHexString(0xFF & digest[i]));
                } else {
                    stringBuffer.append(Integer.toHexString(0xFF & digest[i]));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString().toUpperCase();
    }

    /**
     * 获取sha1签名信息
     *
     * @param pm
     */
    private String getSHA1Signatures(PackageManager pm) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            MessageDigest md5 = MessageDigest.getInstance("SHA1");
            md5.reset();
            md5.update(signatures[0].toByteArray());
            byte[] digest = md5.digest();

            for (int i = 0; i < digest.length; i++) {
                //转16进制
                if (Integer.toHexString(0xFF & digest[i]).length() == 1) {
                    //1位数，前面补0
                    stringBuffer.append("0").append(Integer.toHexString(0xFF & digest[i]));
                } else {
                    stringBuffer.append(Integer.toHexString(0xFF & digest[i]));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString().toUpperCase();
    }

    /**
     * 获取sha256签名信息
     *
     * @param pm
     */
    private String getSHA256Signatures(PackageManager pm) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            MessageDigest md5 = MessageDigest.getInstance("SHA256");
            md5.reset();
            md5.update(signatures[0].toByteArray());
            byte[] digest = md5.digest();

            for (int i = 0; i < digest.length; i++) {
                //转16进制
                if (Integer.toHexString(0xFF & digest[i]).length() == 1) {
                    //1位数，前面补0
                    stringBuffer.append("0").append(Integer.toHexString(0xFF & digest[i]));
                } else {
                    stringBuffer.append(Integer.toHexString(0xFF & digest[i]));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString().toUpperCase();
    }
}
