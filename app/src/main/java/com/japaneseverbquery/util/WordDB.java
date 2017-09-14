
package com.japaneseverbquery.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.japaneseverbquery.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author
 * @version 创建时间：2013-3-21 下午11:16:12 类说明
 */
public class WordDB {
    private static final String WORD_SHEEL = "all_word_base.csv";
    private static final String PREFIX_SHEEL = "prefix.dat";
    private Context mContext;
    private HashMap<String, Word> mMap;
    private ArrayList<String> mKanaList;
    private HashMap<String, LinkedList<String>> mKanaKanjiMap;

    private WordDB(Context context) {
        mContext = context;
        mMap = new HashMap<>(1000, 0.6f);
        mKanaList = new ArrayList<>(400);
        mKanaKanjiMap = new HashMap<>();
        try {
            InputStream inStream = mContext.getAssets().open(WORD_SHEEL);
            BufferedReader dbReader = new BufferedReader(new InputStreamReader(
                    inStream));
            String buffer = null;
            String type = null;
            while ((buffer = dbReader.readLine()) != null) {
                if (buffer.length() <= 2) {
                    continue;
                }
                if (!buffer.startsWith(",")) {
                    type = buffer.substring(0, 4);
                    buffer = buffer.substring(4);
                }
                insertWordToMap(buffer, type);
            }
            inStream.close();
            inStream = mContext.getAssets().open(PREFIX_SHEEL);
            dbReader = new BufferedReader(new InputStreamReader(
                    inStream));
            while ((buffer = dbReader.readLine()) != null) {
                String[] pair = buffer.split(" ");
                String kana = pair[0];
                mKanaList.add(kana);
                if (!mKanaKanjiMap.containsKey(kana)) {
                    LinkedList<String> kanjiList = new LinkedList<>();
                    String[] kanjiSet = pair[1].split(",");
                    for (String kanji : kanjiSet) {
                        if (kanji.length() > 0) {
                            kanjiList.add(kanji);
                        }
                    }
                    mKanaKanjiMap.put(kana, kanjiList);
                }
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertWordToMap(String line, String type) {
        line = line.substring(2);
        String[] elem = line.split(",");
        if(elem.length==0){
            return;
        }
        String baseType = elem[0];
        mMap.put(baseType, new Word(elem, type, mContext));
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
        // TODO how can make it fast
        String[] kanjis = new String[] {
                prefix
        };

        LinkedList<String> matchWord = new LinkedList<String>();
        if (prefix.length() != 0) {
            if (prefix.length() >= 1 && WordDB.allInKana(prefix)) {
                log(prefix + " is all in kana");
                String[] transToKanjiResult = transToKanji(prefix);
                if (transToKanjiResult != null) {
                    kanjis = transToKanjiResult;
                }
            }

            for (String prefixItem : kanjis) {
                matchWord.addAll(getMatchedArrayByKanji(prefixItem));
            }
        }
        return matchWord;
    }

    private LinkedList<String> getMatchedArrayByKanji(String prefixItem) {
        Set<String> baseType = mMap.keySet();
        LinkedList<String> list = new LinkedList<>();
        log("get" + prefixItem);
        for (String key : baseType) {
            if (key.startsWith(prefixItem)) {
                list.add(key);
            }
        }
        if (list.size() == 0 && prefixItem.length() > 1) {
            return getMatchedArrayByKanji(prefixItem.substring(0, 1));
        }
        return list;
    }

    static public void log(String logText) {
        if (BuildConfig.DEBUG) {
            Log.i(Constant.TAG, logText);
        }
    }

    public Word getWord(String stringExtra) {
        return mMap.get(stringExtra);
    }

    private static final String CON_REC = "CONREC";
    private static SharedPreferences sSPref;

    private SharedPreferences getSPref() {
        if (sSPref == null) {
            synchronized (SharedPreferences.class) {
                if (sSPref == null) {
                    sSPref = mContext.getSharedPreferences(CON_REC, 0);
                }
            }
        }
        return sSPref;
    }

    static public boolean allInKana(String prefix) {
        for (int i = prefix.length() - 1; i >= 0; i--) {
            int key = prefix.charAt(i);
            if (key < 0x3040 || key > 0x30ff) {
                return false;
            }
        }
        return true;
    }

    public String[] transToKanji(String prefix) {
        int prefixLength = prefix.length();
        int endIndex = getEndSearchIndexByLength(prefixLength+1);
        log(String.format("get %s result is %d", prefix, endIndex));
        for (int i = endIndex; i >= 0; i--) {
            String kana = mKanaList.get(i);
            if (prefix.startsWith(kana)) {
                log(prefix+" find it "+kana);
                LinkedList<String> kanjilinkedList = this.mKanaKanjiMap.get(kana);
                return kanjilinkedList.toArray(new String[kanjilinkedList.size()]);
            }
        }
        return null;
    }

    private int getEndSearchIndexByLength(int prefixLength) {
        int low = 0, high = this.mKanaList.size() - 1;
        while (low < high)
        {
            int mid = low + ((high - low + 1) >> 1);
            if (this.mKanaList.get(mid).length() < prefixLength)
                low = mid;
            else
                // A[mid] >= target
                high = mid - 1;
        }
        return this.mKanaList.get(low).length() < prefixLength ? low : -1;
    }
}
