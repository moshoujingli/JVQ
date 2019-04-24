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
import android.content.Intent
import android.net.Uri


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

    }

    fun setChangeListener(textWatcher: TextWatcher) {
        mInputBox!!.addTextChangedListener(textWatcher)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_home, menu)
        return true
    }

    fun openPrivacyPolicy(v: View) {
        val uri = Uri.parse("https://jvq-privacy.oss-cn-hongkong.aliyuncs.com/UserAgreement.html")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)

    }

    fun openUserAgreement(v: View) {
        val uri = Uri.parse("https://jvq-privacy.oss-cn-hongkong.aliyuncs.com/PrivacyPolicy.html")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
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
