
package com.japaneseverbquery;

import java.util.List;

import com.japaneseverbquery.adapter.PrefixWordAdapter;
import com.japaneseverbquery.util.Constant;
import com.japaneseverbquery.util.WordDB;
import com.newrelic.agent.android.NewRelic;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;

public class HomeActivity extends ListActivity implements OnItemClickListener {

    private EditText mInputBox;
    private PrefixWordAdapter mPrefixWordAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewRelic.withApplicationToken(
                "AA0a44d061f077ae6e730ef6d06564652d9cf88ec9"
        ).start(this.getApplication());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        WordDB.getInstance(getApplicationContext());
        mInputBox = (EditText) findViewById(R.id.prefix);
        mInputBox.setBackgroundResource(R.drawable.editor);
        setChangeListener(new EndListener());
        mPrefixWordAdapter = new PrefixWordAdapter(this.getApplicationContext());
        View emptyView = getLayoutInflater().inflate(R.layout.empty_hint, null);
        this.getListView().setEmptyView(emptyView);
        HomeActivity.this.getListView().setAdapter(mPrefixWordAdapter);
        this.getListView().setOnItemClickListener(this);
    }

    public void setChangeListener(TextWatcher textWatcher) {
        mInputBox.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    private final class EndListener implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            String prefix = s.toString();
            List<String> matchWord = WordDB.getInstance(getApplicationContext()).getMatchedArray(
                    prefix);
            mPrefixWordAdapter.setTextList(matchWord);
            // show the dict type of the word.
            mPrefixWordAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
        Intent intent = new Intent(this, WordDetailActivity.class);
        intent.putExtra(Constant.BASE_TYPE, (String) view.getTag());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
