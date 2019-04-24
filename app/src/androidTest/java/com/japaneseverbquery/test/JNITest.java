package com.japaneseverbquery.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.test.runner.AndroidJUnit4;

import com.japaneseverbquery.util.ImageJNIBridge;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by bixiaopeng on 2017/9/22.
 */
@RunWith(AndroidJUnit4.class)
public class JNITest {
    @Test
    public void getImageContent() {
        Bitmap bitmap = Bitmap.createBitmap(60, 60, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.GREEN);
        ByteBuffer buffer = ByteBuffer.allocateDirect(16);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        ImageJNIBridge.getImageFirstSample(bitmap, buffer);
        buffer.rewind();
        byte[] a =new byte[16];
        buffer.get(a);
        System.out.print(a[1]);
    }
}
