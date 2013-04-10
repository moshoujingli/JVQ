
package com.japaneseverbquery.test;

import junit.framework.Assert;

import com.japaneseverbquery.HomeActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author
 * @version 创建时间：2013-3-21 下午10:01:44 类说明
 */
public class InputCompTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    public InputCompTest() {
        super(HomeActivity.class);
    }

    private HomeActivity mActivity;
    private String mCurContent;

    @Override
    protected void setUp() throws Exception {
        mActivity = getActivity();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testTextChangeListener() throws Throwable {
        mActivity.setChangeListener(new TestEndListener());
        final EditText input = (EditText) mActivity.findViewById(com.japaneseverbquery.R.id.prefix);
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
                // 働き　行く　働く
                String inputText = "働";
                input.setText(inputText);
                Assert.assertEquals(inputText, mCurContent);
                input.append("き");
                Assert.assertEquals(mCurContent, "働き");
                inputText = "行く";
                input.setText(inputText);
                Assert.assertEquals(inputText, mCurContent);

            }

        });
    }

    private final class TestEndListener implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            mCurContent = s.toString();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
        }
    }
}
