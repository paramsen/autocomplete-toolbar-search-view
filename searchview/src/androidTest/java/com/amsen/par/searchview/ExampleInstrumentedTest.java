package com.amsen.par.searchview;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation progressbar_raw_layout, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under progressbar_raw_layout.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.amsen.par.searchview.progressbar_raw_layout", appContext.getPackageName());
    }
}
