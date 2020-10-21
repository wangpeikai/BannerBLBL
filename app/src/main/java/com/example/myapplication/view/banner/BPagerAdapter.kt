package com.example.myapplication.view.banner

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import java.util.*

internal class BPagerAdapter(private val imageViewList: ArrayList<ImageView>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val newPosition = position % imageViewList.size
        val img = imageViewList[newPosition]
        container.addView(img)
        return img
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

}