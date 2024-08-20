package com.example.youtubeproject.presentation.ui

import android.os.Build
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.databinding.ActivityMainBinding
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag
import com.example.youtubeproject.presentation.ui.navigation.TabTag
import com.example.youtubeproject.presentation.ui.viewpager.MainViewPager
import com.google.android.material.tabs.TabLayout.Tab
import java.util.Stack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mCurrentTab : TabTag
    private var fragmentStack = HashMap<TabTag, Stack<FragmentTag>>()

    private var backPressedTime = 0L

    private val onBackPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val curStack = fragmentStack[mCurrentTab]!!
            if(curStack.size == 0) {
                if(mCurrentTab == TabTag.Home) {
                    finishApp()
                } else {
                    mCurrentTab = TabTag.Home
                    binding.viewPager.currentItem = 0
                }
            } else popFragments()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(onBackPressedCallback)

        initViewPager()
        setNavigation()
        setOnBottomBarClick()
    }


    private fun initViewPager() {
        val viewPager = binding.viewPager
        val viewPagerAdapter = MainViewPager(this)
        viewPager.adapter = viewPagerAdapter

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomMenuBar.menu.getItem(position).isChecked = true
            }
        })
    }
    private fun setNavigation() {
        mCurrentTab = TabTag.Home
        fragmentStack[TabTag.Home] = Stack<FragmentTag>()
        fragmentStack[TabTag.Search] = Stack<FragmentTag>()
        fragmentStack[TabTag.Playlist] = Stack<FragmentTag>()
        fragmentStack[TabTag.MyPage] = Stack<FragmentTag>()
    }

    private fun setOnBottomBarClick() {
        binding.bottomMenuBar.setOnItemSelectedListener { item ->
            hideAllCurStack(mCurrentTab)
            mCurrentTab = when(item.itemId) {
                R.id.bottom_menu_home -> {
                    binding.viewPager.currentItem = 0
                    TabTag.Home
                }
                R.id.bottom_menu_search -> {
                    binding.viewPager.currentItem = 1
                    TabTag.Search
                }
                R.id.bottom_menu_playlist -> {
                    binding.viewPager.currentItem = 2
                    TabTag.Playlist
                }
                R.id.bottom_menu_my_page -> {
                    binding.viewPager.currentItem = 3
                    TabTag.MyPage
                }
                else -> return@setOnItemSelectedListener false
            }
            showAllStack(mCurrentTab)

            true
        }

        binding.bottomMenuBar.setOnItemReselectedListener {
            removeAllCurStack(mCurrentTab)
        }
    }

    //tag: Enum Data인 TabTag를 넘겨줘 해당 Stack에 push 한다.
    //fragment: 원하는 Fragment를 파라미터로 넘긴다.
    fun pushFragments(fragment: Fragment, tag: FragmentTag) {
        if(fragmentStack[mCurrentTab]!!.size != 0) {
            supportFragmentManager.findFragmentByTag(
                fragmentStack[mCurrentTab]!!.peek().name
            )?.let {
                supportFragmentManager.beginTransaction()
                    .hide(it)
                    .add(R.id.fragment_container_view, fragment, tag.name).commit()
            }
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, fragment, tag.name).commit()
        }

        Log.d("Navigation", "Push Fragment: ${tag.name} to Stack: ${mCurrentTab.name}")
        fragmentStack[mCurrentTab]!!.push(tag)
    }
    //현재 탭에서 하나를 pop한다. 여기서 현재 탭은 currentTab을 이용해 알 수 있다.
    fun popFragments() {
        val curStack = fragmentStack[mCurrentTab]!!
        if(curStack.size == 0) return

        val curFragment = supportFragmentManager.findFragmentByTag(
            curStack.lastElement().name
        )!!

        if(curStack.size == 1) {
            supportFragmentManager.beginTransaction()
                .remove(curFragment)
                .commit()
        } else {
            val tag = curStack.elementAt(curStack.size - 2).name
            val nextFragment = supportFragmentManager.findFragmentByTag(tag)!!
            supportFragmentManager.beginTransaction()
                .remove(curFragment)
                .show(nextFragment)
                .commit()
        }

        Log.d("Navigation", "size: ${curStack.size}")
        fragmentStack[mCurrentTab]!!.pop()
    }

    private fun finishApp() {
        if(System.currentTimeMillis() - backPressedTime >= 2000L) {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finishAffinity()
        }
    }

    private fun removeAllCurStack(tag: TabTag) {
        Log.d("Navigation", "Remove All Fragments in tab: ${tag.name}")
        fragmentStack[tag]!!.forEach {
            Log.d("Navigation", "Tag: ${it.name}")
            val fragment = supportFragmentManager.findFragmentByTag(it.name)!!
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
        fragmentStack[tag]!!.clear()
    }


    private fun hideAllCurStack(tag: TabTag) {
        Log.d("Navigation", "Hide All Fragments in tab: ${tag.name}")
        fragmentStack[tag]!!.forEach {
            Log.d("Navigation", "Tag: ${it.name}")
            val fragment = supportFragmentManager.findFragmentByTag(it.name)!!
            supportFragmentManager.beginTransaction()
                .hide(fragment)
                .commit()
        }
    }
    private fun showAllStack(tag: TabTag) {
        Log.d("Navigation", "Show All Fragments in tab: ${tag.name}")
        fragmentStack[tag]!!.forEach {
            Log.d("Navigation", "Tag: ${it.name}")
            val fragment = supportFragmentManager.findFragmentByTag(it.name)!!
            supportFragmentManager.beginTransaction()
                .show(fragment)
                .commit()
        }
    }
}
