package com.japaneseverbquery.util;

import com.japaneseverbquery.R;

import android.content.Context;

/**
 * @author
 * @version 创建时间：2013-3-21 下午11:41:02 类说明
 */
public class Word {

	private String[] mElement;
	private String mType;
	private Context mContext;

	public Word(String[] elem, String type, Context context) {
		mElement = elem;
		mType = type;
		mContext = context;
	}

	@Override
	public String toString() {
		return mContext.getResources().getString(R.string.detail_tmpl, mType,
				mElement[1], mElement[0], mElement[2], mElement[3],
				mElement[4], mElement[5], mElement[6], mElement[7],
				mElement[8], mElement[9], mElement[10], mElement[11]);
	}

}
