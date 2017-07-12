package com.example.hasee.cam1basic;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.widget.Toast;

import org.opencv.android.JavaCameraView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hasee on 2017/7/12.
 */

public class CTCameraView extends JavaCameraView implements Camera.AutoFocusCallback{

    private static final String TAG = "CAM1BASIC: CTCameraView";
    public CTCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void setFocusMode(Context item, int type) {
        Camera.Parameters params = mCamera.getParameters();
        List<String> FocusModes = params.getSupportedFocusModes();
        switch (type){
            case 0:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                    params.setAutoExposureLock(false);
                     params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    Toast.makeText(item, "Set Auto Mode done", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(item, "Auto Mode not supported", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                    Toast.makeText(item, "Set Continuous Mode done", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(item, "Continuous Mode not supported", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_EDOF)) {
                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_EDOF);
                    Toast.makeText(item, "Set EDOF Mode done", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(item, "EDOF Mode not supported", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
                    params.setAutoExposureLock(true);
                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
                    Toast.makeText(item, "Set Fixed Mode done", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(item, "Fixed Mode not supported", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {
                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
                    Toast.makeText(item, "Set Infinity Mode done", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(item, "Infinity Mode not supported", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_MACRO)) {
                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
                    Toast.makeText(item, "Set Macro Mode done", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(item, "Macro Mode not supported", Toast.LENGTH_SHORT).show();
                break;
        }


        mCamera.setParameters(params);
    }

    void setISOMode(Context item, String value) {
        Camera.Parameters params = mCamera.getParameters();
        params.setAutoExposureLock(true);

        String flat = params.flatten();
        String[] isoValues = null;
        String values_keyword=null;
        String iso_keyword=null;
        if(flat.contains("iso-values")) {
            // most used keywords
            values_keyword="iso-values";
            iso_keyword="iso";
        } else if(flat.contains("iso-mode-values")) {
            // google galaxy nexus keywords
            values_keyword="iso-mode-values";
            iso_keyword="iso";
        } else if(flat.contains("iso-speed-values")) {
            // micromax a101 keywords
            values_keyword="iso-speed-values";
            iso_keyword="iso-speed";
        } else if(flat.contains("nv-picture-iso-values")) {
            // LG dual p990 keywords
            values_keyword="nv-picture-iso-values";
            iso_keyword="nv-picture-iso";
        }
// add other eventual keywords here...
        if(iso_keyword!=null) {
            // flatten contains the iso key!!
            String iso = flat.substring(flat.indexOf(values_keyword));
            iso = iso.substring(iso.indexOf("=")+1);

            if(iso.contains(";")) iso = iso.substring(0, iso.indexOf(";"));

            isoValues = iso.split(",");
            List<String> isoModes = Arrays.asList(isoValues);
            if (isoModes.contains(value)) {
                params.set(iso_keyword, value);
                Toast.makeText(item, "set "+value+" done",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(item, value+" not supported",Toast.LENGTH_SHORT).show();
            }
            mCamera.setParameters(params);
        } else {
            // iso not supported in a known way
            Toast.makeText(item, "iso not supported in a known way",Toast.LENGTH_SHORT).show();
            return;
        }

    }
    @Override
    public void onAutoFocus(boolean success, Camera camera) {

    }
}
