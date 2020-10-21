package com.example.myapplication.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.imgRsc
import com.example.myapplication.utils.GlideCacheUtil
import com.example.myapplication.view.banner.AutoScroll
import com.example.myapplication.view.banner.BPagerAdapter
import com.example.myapplication.view.banner.BScroller
import kotlinx.android.synthetic.main.activity_banner.*
import java.util.*

class BannerActivity : AppCompatActivity() {

    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        initBanner()
    }

    private fun initBanner() {
        val imageViewArray = ArrayList<ImageView>()
        var dot: View? = null
        var layoutParams: LinearLayout.LayoutParams? = null
        for (i in imgRsc.indices) {
            val imageView = ImageView(this)
            Glide.with(this).load(imgRsc[i]).apply(
                RequestOptions()
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            ).into(imageView)
            imageView.id = i
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setOnClickListener {
                Toast.makeText(this, "您点击的图片是:${imageView.id}", Toast.LENGTH_SHORT).show()
            }
            imageViewArray.add(imageView)
            dot = View(this)
            dot.setBackgroundResource(R.drawable.dot)
            layoutParams = LinearLayout.LayoutParams(40, 40)
            dot.isEnabled = false
            dot_container.addView(dot, layoutParams)
        }

        dot_container.getChildAt(0).isEnabled = true
        currentPosition = 0
        bViewPager.adapter =
            BPagerAdapter(imageViewArray)
        bViewPager.currentItem = Int.MAX_VALUE / 2 - Int.MAX_VALUE / 2 % imgRsc.size
        BScroller(this).setScrollDuration(bViewPager, 1000)
        bViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                val newPosition: Int = position % imgRsc.size
                dot_container.getChildAt(currentPosition).isEnabled = false
                dot_container.getChildAt(newPosition).isEnabled = true
                currentPosition = newPosition
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })

    }

    fun onButtonClick(view: View) {
        when (view) {
            btn_start_scroll -> {
                startAutoScroll()
                Toast.makeText(this, "自循环已启动", Toast.LENGTH_SHORT).show()
            }
            btn_stop_scroll -> {
                stopAutoScroll()
                Toast.makeText(this, "自循环已关闭", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 启动自循环
     */
    private fun startAutoScroll() {
        AutoScroll.instance()
            .run(this, bViewPager)
    }

    /**
     * 关闭自循环
     */
    private fun stopAutoScroll() {
        AutoScroll.instance().stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAutoScroll()
        //如果Glide有缓存 清除缓存
        GlideCacheUtil.instance?.clearImageAllCache(this)
    }
}