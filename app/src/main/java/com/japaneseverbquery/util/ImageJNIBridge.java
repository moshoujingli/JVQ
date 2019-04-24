package com.japaneseverbquery.util;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;

/**
 * Created by bixiaopeng on 2017/9/22.
 */

public class ImageJNIBridge {
    static {
        System.loadLibrary("_calc");
    }
    native static public void getImageFirstSample(Bitmap image, ByteBuffer result);
}
