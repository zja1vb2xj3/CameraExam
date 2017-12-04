package com.example.pdg.qr_reader2;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by pdg on 2017-12-01.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Camera camera;

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera = Camera.open();

        this.setOnClickListener(onClickListener);

        try {
            camera.setPreviewDisplay(holder);

            camera.setDisplayOrientation(90);

            Camera.Parameters parameters = camera.getParameters();

//            int maxZoom = parameters.getMaxZoom();
//            System.out.println("maxZoom : " + maxZoom);
//
            int zoom = parameters.getZoom();

            System.out.println("zoom : " + zoom);
            parameters.setZoom(zoom);

//            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
//
            camera.setParameters(parameters);

            camera.startPreview();

        } catch (IOException e) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
        System.out.println("surfaceChanged");
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera = null;
    }

    SurfaceView.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };
}
