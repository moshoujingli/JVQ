
package com.japaneseverbquery;

import java.util.Collections;
import java.util.List;

import com.japaneseverbquery.adapter.PrefixWordAdapter;
import com.japaneseverbquery.util.Constant;
import com.japaneseverbquery.util.WordDB;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
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
    private CheckBox mUseConRecCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        WordDB.getInstance(getApplicationContext());
        mUseConRecCheckBox = (CheckBox) findViewById(R.id.conr);
        mUseConRecCheckBox.setChecked(WordDB.getInstance(getApplicationContext())
                .needConcessionRecognize());
        mUseConRecCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                WordDB.getInstance(getApplicationContext()).setConcessionRecognize(arg1);

            }
        });
        mInputBox = (EditText) findViewById(R.id.prefix);
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
            List<String> matchWord = null;
            if (prefix.length() == 0) {
                matchWord = Collections.emptyList();
            } else {
                // search for the line
                matchWord = WordDB.getInstance(getApplicationContext()).getMatchedArray(prefix);
            }
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
    }
}
