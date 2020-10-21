package com.example.myapplication.view.banner

import android.content.Context
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

class BScroller(mContext: Context) : Scroller(mContext) {

    private var mDuration = 800

    fun setScrollDuration(viewPager: ViewPager, duration: Int) {
        mDuration = duration
        try {
            val scroller = ViewPager::class.java.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller[viewPager] = this
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

}