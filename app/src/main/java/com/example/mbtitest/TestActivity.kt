package com.example.mbtitest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    val questionnaireResults = QuestionnairResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this)

        // 화면을 터치로 끌어서 옆 페이지로 넘기는 것을 막는다.(다음 버튼을 눌렀을 때 넘어가야 하니까)
        viewPager.isUserInputEnabled = false

    }

    fun moveToNextQuestion(){
        
        if(viewPager.currentItem == 3) {

            // 마지막페이지 -> 결과 화면으로 이동

            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("results", ArrayList(questionnaireResults.results))
            startActivity(intent)

        } else {
            val nextItem = viewPager.currentItem+1
            if(nextItem < viewPager.adapter?.itemCount ?:0) {
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }

}

class QuestionnairResults{
    val results = mutableListOf<Int>()

    fun addResponses(response: List<Int>) { // 1,1,2
        val mostFrequent = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequent?.let { results.add(it) }
    }
}
