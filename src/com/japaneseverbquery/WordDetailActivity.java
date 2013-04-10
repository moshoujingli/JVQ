
package com.japaneseverbquery;

import com.japaneseverbquery.util.Constant;
import com.japaneseverbquery.util.Word;
import com.japaneseverbquery.util.WordDB;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author
 * @version 创建时间：2013-3-22 上午12:25:12 类说明
 */
public class WordDetailActivity extends Activity {

    private TextView mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_detail);
        mDetail = (TextView) findViewById(R.id.word_detail);
        Word word = WordDB.getInstance(getApplicationContext()).getWord(
                getIntent().getStringExtra(Constant.BASE_TYPE));
        mDetail.setText(word.toString());
    }

}
