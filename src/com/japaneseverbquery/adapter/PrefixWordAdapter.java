package com.japaneseverbquery.adapter;

import java.util.LinkedList;
import java.util.List;

import com.japaneseverbquery.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author 
 * @version 创建时间：2013-3-21 下午10:46:57
 * 类说明
 */
public class PrefixWordAdapter extends BaseAdapter {

	private List<String> mMatchWord = new LinkedList<String>();
	private Context mContext;

	public PrefixWordAdapter(Context applicationContext) {
		mContext = applicationContext;
	}

	@Override
	public int getCount() {
		return mMatchWord.size();
	}

	@Override
	public Object getItem(int pos) {
		return mMatchWord.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = View.inflate(mContext, R.layout.word_item, null);
		}
		TextView word = (TextView)convertView.findViewById(R.id.word_dict_type);
		word.setText(mMatchWord.get(position));
		convertView.setTag(word.getText());
		return convertView;
	}

	public void setTextList(List<String> matchWord) {
		mMatchWord  = matchWord;
	}

}
