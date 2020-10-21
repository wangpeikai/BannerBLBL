package com.example.myapplication.view.banner

import android.app.Activity
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AutoScroll {

    companion object {
        private var autoScroll: AutoScroll? = null
        fun instance(): AutoScroll {
            if (autoScroll == null) {
                synchronized(AutoScroll::class.java) {
                    if (autoScroll == null) {
                        autoScroll =
                            AutoScroll()
                    }
                }
            }
            return autoScroll!!
        }
    }

    private var job: Job? = null

    private var mViewPager: ViewPager? = null

    fun run(activity: Activity, viewPager: ViewPager) {
        if (job != null) {
            return
        }
        mViewPager = viewPager
        job = GlobalScope.launch {
            while (true) {
                delay(4000)
                activity.runOnUiThread {
                    mViewPager!!.currentItem = mViewPager!!.currentItem + 1
                }
            }
        }
    }

    fun stop() {
        job?.cancel().apply {
            job = null
        }
    }

}