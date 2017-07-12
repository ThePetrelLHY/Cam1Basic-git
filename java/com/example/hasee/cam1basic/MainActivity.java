package com.example.hasee.cam1basic;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends Activity implements JavaCameraView.CvCameraViewListener2 {
    private static final String TAG = "CAM1BASIC::Activity";

    private CTCameraView mOpenCvCameraView;
    private Button mTakePicBtn;
    private Button mTakePicBtn1;

    public MainActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    //OpenCV库加载并初始化成功后的回调函数
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            // TODO Auto-generated method stub
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    Log.i(TAG, "成功加载");
                    mOpenCvCameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    Log.i(TAG, "加载失败");
                    break;
            }
        }
    };
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        mOpenCvCameraView = (CTCameraView) findViewById(R.id.tutorial1_activity_java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mTakePicBtn = (Button) findViewById(R.id.takePic_button);
        mTakePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mOpenCvCameraView.setISOMode(MainActivity.this, "ISO100");
                mOpenCvCameraView.setFocusMode(MainActivity.this, 3);
            }
        });

        mTakePicBtn1 = (Button) findViewById(R.id.takePic_button1);
        mTakePicBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mOpenCvCameraView.setISOMode(MainActivity.this, "ISO800");
                mOpenCvCameraView.setFocusMode(MainActivity.this, 0);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "onResume: Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_10, this, mLoaderCallback);
        } else {
            Log.d(TAG, "onResume: OpenCV library found inside package. Using it");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }
}
