package com.japaneseverbquery.util

import com.japaneseverbquery.JVQApp
import com.japaneseverbquery.R

/**
 * @author bixiaopeng
 * *
 * @version 创建时间：2013-3-21 下午11:41:02 类说明
 */
class Word(private val mElement: Array<String>, private val mType: String) {

    override fun toString(): String {
        return JVQApp.application!!.resources.getString(R.string.detail_tmpl, mType,
                mElement[1], mElement[0], mElement[2], mElement[3],
                mElement[4], mElement[5], mElement[6], mElement[7],
                mElement[8], mElement[9], mElement[10], mElement[11])
    }

}
