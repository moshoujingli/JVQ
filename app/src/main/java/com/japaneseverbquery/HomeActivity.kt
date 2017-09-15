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
import com.appsflyer.AppsFlyerLib
import com.japaneseverbquery.adapter.PrefixWordAdapter
import com.japaneseverbquery.util.WordDB

class HomeActivity : ListActivity(), OnItemClickListener {

    private var mInputBox: EditText? = null
    private var mPrefixWordAdapter: PrefixWordAdapter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAppsflyer()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_home)
        mInputBox = findViewById(R.id.prefix) as EditText
        mInputBox!!.setBackgroundResource(R.drawable.editor)
        setChangeListener(EndListener())
        mPrefixWordAdapter = PrefixWordAdapter(this.applicationContext)
        val emptyView = layoutInflater.inflate(R.layout.empty_hint, null)
        this.listView.emptyView = emptyView
        this.listView.adapter = mPrefixWordAdapter
        this.listView.onItemClickListener = this

    }

    private fun startAppsflyer() {
        AppsFlyerLib.getInstance().startTracking(this.application, "cE5Ac4a3oKGCfpF7okAKnJ")
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

            val matchWord = WordDB.getMatchedArray(prefix)
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
        WordDetailActivity.startWordDetailActivity(this, view.tag as String)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
