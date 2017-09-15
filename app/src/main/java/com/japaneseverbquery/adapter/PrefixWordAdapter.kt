package com.japaneseverbquery.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.japaneseverbquery.R
import java.util.*

/**
 * @author bixiaopeng
 * @version 创建时间：2013-3-21 下午10:46:57 类说明
 */
class PrefixWordAdapter(private val mContext: Context) : BaseAdapter() {

    private var mMatchWord: List<String> = LinkedList()

    override fun getCount(): Int {
        return mMatchWord.size
    }

    override fun getItem(pos: Int): Any {
        return mMatchWord[pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.word_item, null)
        }
        val word = convertView!!.findViewById(R.id.word_dict_type) as TextView
        word.text = mMatchWord[position]
        convertView.tag = word.text
        return convertView
    }

    fun setTextList(matchWord: List<String>) {
        mMatchWord = matchWord
    }

}
