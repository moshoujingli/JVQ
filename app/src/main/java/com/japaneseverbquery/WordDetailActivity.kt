package com.japaneseverbquery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import com.japaneseverbquery.util.WordDB

/**
 * @author bixiaopeng
 * @version 创建时间：2013-3-22 上午12:25:12 类说明
 */
class WordDetailActivity : Activity() {


    companion object STATIC {
        val WORD_EXTRA = "WORD_EXTRA"
        fun startWordDetailActivity(context: Context, word: String) {
            val intent = Intent(context, WordDetailActivity::class.java)
            intent.putExtra(WORD_EXTRA, word)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.word_detail)
        val detail = findViewById(R.id.word_detail) as TextView
        val wordTitle = intent.getStringExtra(WORD_EXTRA)
        val word = WordDB.getWord(wordTitle)
        detail.text = word!!.toString()
        val title = findViewById(R.id.title) as TextView
        title.text = wordTitle
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
