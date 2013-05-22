
package com.japaneseverbquery.test;

import junit.framework.Assert;

import com.japaneseverbquery.util.WordDB;

import android.test.InstrumentationTestCase;
import android.util.Log;

public class WordSearchTest extends InstrumentationTestCase {
    private WordDB mWordDb;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mWordDb = WordDB.getInstance(this.getInstrumentation().getTargetContext());
        mWordDb.setConcessionRecognize(true);

    }

    public void testSearchSpeed() {
        String[] keys = {"落とします","飼う","ご覧になる","過ごし","働きます","引っ張","持ち歩きます","通じま","遅れま","しゃれます","わくわく","はつげんして","みせて"};
        for(String key:keys){
            Assert.assertTrue(key, mWordDb.getMatchedArray(key).size()>0);
        }
    }
}
