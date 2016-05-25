
package com.japaneseverbquery;

import com.japaneseverbquery.util.Constant;
import com.japaneseverbquery.util.Word;
import com.japaneseverbquery.util.WordDB;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * @author bixiaopeng
 * @version 创建时间：2013-3-22 上午12:25:12 类说明
 */
public class WordDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.word_detail);
        TextView detail = (TextView) findViewById(R.id.word_detail);
        String wordTitle = getIntent().getStringExtra(Constant.BASE_TYPE);
        Word word = WordDB.getInstance(getApplicationContext()).getWord(
                wordTitle);
        detail.setText(word.toString());
        TextView title = (TextView)findViewById(R.id.title);
        title.setText(wordTitle);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
