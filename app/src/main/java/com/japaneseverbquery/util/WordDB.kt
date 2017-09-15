package com.japaneseverbquery.util

import android.content.SharedPreferences
import com.japaneseverbquery.JVQApp
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * @author bixiaopeng
 * @version 创建时间：2013-3-21 下午11:16:12 类说明
 */
object WordDB {
    private val mMap: HashMap<String, Word> = HashMap(1000, 0.6f)
    private val mKanaList: ArrayList<String> = ArrayList(400)
    private val mKanaKanjiMap: HashMap<String, LinkedList<String>> = HashMap()
    private val WORD_SHEEL = "all_word_base.csv"
    private val PREFIX_SHEEL = "prefix.dat"
    private val CON_REC = "CONREC"
    private val sSPref: SharedPreferences = JVQApp.application!!.getSharedPreferences(CON_REC, 0)

    private val TAG = "WordDB";

    init {

        try {

            var inStream = JVQApp.application!!.assets.open(WORD_SHEEL)
            var dbReader = BufferedReader(InputStreamReader(
                    inStream))

            generateSequence { dbReader.readLine() }
                    .filter { it.length > 20 }
                    .forEach {
                        val type = it.substring(0, 4)
                        val line = it.substring(6)
                        insertWordToMap(line, type)
                    }

            inStream.close()
            inStream = JVQApp.application!!.assets.open(PREFIX_SHEEL)
            dbReader = BufferedReader(InputStreamReader(
                    inStream))


            generateSequence { dbReader.readLine() }
                    .forEach {
                        val pair = it.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val kana = pair[0]
                        mKanaList.add(kana)
                        if (!mKanaKanjiMap.containsKey(kana)) {
                            val kanjiSet = pair[1].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            val kanjiList = kanjiSet.filterTo(LinkedList<String>()) { it.isNotEmpty() }
                            mKanaKanjiMap.put(kana, kanjiList)
                        }
                    }
            inStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun insertWordToMap(line: String, type: String) {
        val elem = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (elem.isEmpty()) {
            return
        }
        val baseType = elem[0]
        mMap.put(baseType, Word(elem, type))
    }

    fun getMatchedArray(prefix: String): List<String> {
        // TODO how can make it fast
        var kanjis = arrayOf(prefix)
        if (prefix.isEmpty()) {
            return emptyList()
        }

        val matchWord = LinkedList<String>()
        if (prefix.isNotEmpty()) {
            if (prefix.isNotEmpty() && WordDB.allInKana(prefix)) {
                LogHelper.i(TAG, prefix + " is all in kana")
                val transToKanjiResult = transToKanji(prefix)
                if (transToKanjiResult != null) {
                    kanjis = transToKanjiResult
                }
            }

            for (prefixItem in kanjis) {
                matchWord.addAll(getMatchedArrayByKanji(prefixItem))
            }
        }
        return matchWord
    }

    private fun getMatchedArrayByKanji(prefixItem: String): List<String> {
        val baseType = mMap.keys
        LogHelper.i(TAG, "get" + prefixItem)

        val list = baseType.filter { it.startsWith(prefixItem) }.toList()

        return if (list.isEmpty() && prefixItem.length > 1) {
            getMatchedArrayByKanji(prefixItem.substring(0, 1))
        } else list
    }

    fun getWord(stringExtra: String): Word? {
        return mMap[stringExtra]
    }


    fun transToKanji(prefix: String): Array<String>? {
        val prefixLength = prefix.length
        val endIndex = getEndSearchIndexByLength(prefixLength + 1)
        LogHelper.i(TAG, String.format("get %s result is %d", prefix, endIndex))
        for (i in endIndex downTo 0) {
            val kana = mKanaList[i]
            if (prefix.startsWith(kana)) {
                LogHelper.i(TAG, prefix + " find it " + kana)
                val kanjilinkedList = this.mKanaKanjiMap[kana]
                return kanjilinkedList!!.toTypedArray()
            }
        }
        return null
    }

    private fun getEndSearchIndexByLength(prefixLength: Int): Int {
        var low = 0
        var high = this.mKanaList.size - 1
        while (low < high) {
            val mid = low + (high - low + 1 shr 1)
            if (this.mKanaList[mid].length < prefixLength)
                low = mid
            else
            // A[mid] >= target
                high = mid - 1
        }
        return if (this.mKanaList[low].length < prefixLength) low else -1
    }

    fun allInKana(prefix: String): Boolean {
        return prefix.toCharArray().map { it.toInt() }
                .none { it < 0x3040 || it > 0x30ff }
    }
}
