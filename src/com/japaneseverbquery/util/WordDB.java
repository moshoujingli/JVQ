package com.japaneseverbquery.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import android.content.Context;
import android.util.Log;

/**
 * @author
 * @version 创建时间：2013-3-21 下午11:16:12 类说明
 */
public class WordDB {
	private static final String WORD_SHEEL = "all_word_base.csv";
	private Context mContext;
	private HashMap<String, Word> mMap;

	private WordDB(Context context) {
		mContext = context;
		mMap = new HashMap<String, Word>(1000, 0.6f);
		try {
			InputStream inStream = mContext.getAssets().open(WORD_SHEEL);
			BufferedReader dbReader = new BufferedReader(new InputStreamReader(
					inStream));
			String buffer = null;
			String type = null;
			while ((buffer = dbReader.readLine()) != null) {
				if (buffer.length() <= 5) {
					continue;
				}
				if (!buffer.startsWith(",")) {
					type = buffer.substring(0, 4);
					buffer = buffer.substring(4);
				}
				insertWordToMap(buffer, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertWordToMap(String line, String type) {
		line = line.substring(2);
		log(line);
		String[] elem = line.split(",");
		String baseType = elem[0];
		mMap.put(baseType, new Word(elem,type,mContext));
	}

	private static WordDB sInstance = null;

	public static WordDB getInstance(Context context) {
		if (sInstance == null) {
			synchronized (WordDB.class) {
				if (sInstance == null) {
					sInstance = new WordDB(context);
				}
			}
		}
		return sInstance;
	}

	public LinkedList<String> getMatchedArray(String prefix) {
		Set<String> baseType = mMap.keySet();
		LinkedList<String> list = new LinkedList<String>();
		for (String key : baseType) {
			if (key.startsWith(prefix)) {
				list.add(key);
			}
		}
		return list;
	}

	private void log(String logText) {
		if (Constant.DEBUG) {
			Log.i(Constant.TAG, logText);
		}
	}

	public Word getWord(String stringExtra) {
		return mMap.get(stringExtra);
	}
}
