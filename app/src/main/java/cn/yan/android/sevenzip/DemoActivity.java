package cn.yan.android.sevenzip;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.yan.lib.android.sevenzip.SevenZip;

public class DemoActivity extends AppCompatActivity {

    public static final String TAG = "DemoActivity";

    long time = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        ArrayList permissionsToRequire = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequire.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            Log.i(TAG, "[onCreate] request READ_EXTERNAL_STORAGE");
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequire.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Log.i(TAG, "[onCreate] request WRITE_EXTERNAL_STORAGE");
        }
        if (permissionsToRequire.size() > 0) {
            ActivityCompat.requestPermissions(this, (String[]) permissionsToRequire.toArray(new String[0]), 0);
        }

        // Example of a call to a native method
        final Button zipFileTv = (Button) findViewById(R.id.zip_file_tv);
        final Button unzipFileTv = (Button) findViewById(R.id.unzip_file_tv);
        final TextView infoTv = (TextView) findViewById(R.id.info_tv);

        zipFileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SevenZip.zip("/sdcard/qqmusic2/arm64-v8a/libTAR.so", "/sdcard/qqmusic2/arm64-v8a/libTAR.so.7z", new SevenZip.OnStateListener() {
                    @Override
                    public void onStart(String src) {
                        time = System.currentTimeMillis();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoTv.setText("onStarted");
                            }
                        });
                    }

                    @Override
                    public void onChange(String src, float progress) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoTv.setText("onChange src=" + src + ", progress=" + progress);
                            }
                        });

                    }

                    @Override
                    public void onError(String src, int errorCode) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoTv.setText("onError src=" + src + ", errorCode" + errorCode);
                            }
                        });

                    }

                    @Override
                    public void onSuccess(String src) {
                        infoTv.setText("onSuccess cost time=" + (System.currentTimeMillis() - time));
                    }
                });
            }
        });

        unzipFileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SevenZip.unzip("/sdcard/qqmusic2/arm64-v8a/libTAR.so.7z", "/sdcard/qqmusic2/arm64-v8a/libTAR.so", new SevenZip.OnStateListener() {
                    @Override
                    public void onStart(String src) {
                        time = System.currentTimeMillis();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoTv.setText("onStarted");
                            }
                        });
                    }

                    @Override
                    public void onChange(String src, float progress) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoTv.setText("onChange src=" + src + ", progress=" + progress);
                            }
                        });

                    }

                    @Override
                    public void onError(String src, int errorCode) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoTv.setText("onError src=" + src + ", errorCode" + errorCode);
                            }
                        });

                    }

                    @Override
                    public void onSuccess(String src) {
                        infoTv.setText("onSuccess cost time=" + (System.currentTimeMillis() - time));
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You must allow all the permissions.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
