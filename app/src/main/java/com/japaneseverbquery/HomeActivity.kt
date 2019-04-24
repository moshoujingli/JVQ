package com.japaneseverbquery

import android.app.ListActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.japaneseverbquery.adapter.PrefixWordAdapter
import com.japaneseverbquery.util.AppPreference
import com.japaneseverbquery.util.ReportHelper
import com.japaneseverbquery.util.WordDB


class HomeActivity : ListActivity(), OnItemClickListener {

    private var mInputBox: EditText? = null
    private var mPrefixWordAdapter: PrefixWordAdapter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_home)
        mInputBox = findViewById(R.id.prefix)
        mInputBox!!.setBackgroundResource(R.drawable.editor)
        setChangeListener(EndListener())
        mPrefixWordAdapter = PrefixWordAdapter(this.applicationContext)
        val emptyView = layoutInflater.inflate(R.layout.empty_hint, null)
        this.listView.emptyView = emptyView
        this.listView.adapter = mPrefixWordAdapter
        this.listView.onItemClickListener = this

        if (AppPreference.needShowPrivacy()) {
            val privacy: TextView = findViewById(R.id.privacy)
            privacy.visibility = View.VISIBLE
            privacy.setOnClickListener {
                Toast.makeText(this, "For calculate install info, we use and send GAID, plz not use if you matter this.", Toast.LENGTH_LONG).show()
                it.visibility = View.GONE
            }
        }


    }

    fun setChangeListener(textWatcher: TextWatcher) {
        mInputBox!!.addTextChangedListener(textWatcher)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_home, menu)
        return true
    }

    private inner class EndListener : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            val prefix = s.toString()

            if (AppPreference.isFirstInputContent()) {
                ReportHelper.reportFirstInput(prefix)
            }

            val matchWord = WordDB.getMatchedArray(prefix)
            if (matchWord.isEmpty() && prefix.length > 3) {
                ReportHelper.reportNoMatchInput(prefix)
            }
            mPrefixWordAdapter!!.setTextList(matchWord)
            // show the dict type of the word.
            mPrefixWordAdapter!!.notifyDataSetChanged()
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                   count: Int) {
        }
    }

    override fun onItemClick(arg0: AdapterView<*>, view: View, arg2: Int, arg3: Long) {
        val word = view.tag as String
        ReportHelper.reportSelectWord(word)
        WordDetailActivity.startWordDetailActivity(this, word)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
