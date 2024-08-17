package com.example.youtubeproject.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.youtubeproject.R
import com.example.youtubeproject.databinding.ActivityMainBinding
import com.example.youtubeproject.presentation.ui.fragment.HomeFragment
import com.example.youtubeproject.presentation.ui.fragment.MyPageFragment
import com.example.youtubeproject.presentation.ui.fragment.PlaylistFragment
import com.example.youtubeproject.presentation.ui.fragment.SearchFragment
import com.example.youtubeproject.presentation.ui.viewpager.MainViewPager
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mCurrentTab : TabTag
    private var fragmentStack = HashMap<TabTag, Stack<Fragment>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        setNavigation()
        setOnBottomBarClick()
    }

    //tag: Enum Data인 TabTag를 넘겨줘 해당 Stack에 push 한다.
    //fragment: 원하는 Fragment를 파라미터로 넘긴다.
    fun pushFragments(tag: TabTag, fragment: Fragment) {
        fragmentStack[tag]!!.push(fragment)

        supportFragmentManager.beginTransaction()
            .replace(R.id.viewPager, fragment).commit()
    }
    //현재 탭에서 하나를 pop한다. 여기서 현재 탭은 currentTab을 이용해 알 수 있다.
    fun popFragments() {
        val curStack = fragmentStack[mCurrentTab]!!
        if(curStack.size == 1) return

        val fragment: Fragment = curStack.elementAt(fragmentStack[mCurrentTab]!!.size - 2)
        fragmentStack[mCurrentTab]!!.pop()

        supportFragmentManager.beginTransaction()
            .replace(R.id.viewPager, fragment)
            .commit()
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
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.viewPager) as NavHostFragment
//        val navController = navHostFragment.findNavController()
//        binding.bottomMenuBar.setupWithNavController(navController)

        mCurrentTab = TabTag.Home
        fragmentStack[TabTag.Home] = Stack<Fragment>()
        fragmentStack[TabTag.Home]!!.push(HomeFragment())

        fragmentStack[TabTag.Search] = Stack<Fragment>()
        fragmentStack[TabTag.Search]!!.push(SearchFragment())

        fragmentStack[TabTag.Playlist] = Stack<Fragment>()
        fragmentStack[TabTag.Playlist]!!.push(PlaylistFragment())

        fragmentStack[TabTag.MyPage] = Stack<Fragment>()
        fragmentStack[TabTag.MyPage]!!.push(MyPageFragment())
    }
    private fun setOnBottomBarClick() {
        binding.bottomMenuBar.setOnItemSelectedListener { item ->
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

//            val nextFragment = fragmentStack[mCurrentTab]!!.lastElement()
//            pushFragments(mCurrentTab, nextFragment!!)

            true
        }
    }
}
