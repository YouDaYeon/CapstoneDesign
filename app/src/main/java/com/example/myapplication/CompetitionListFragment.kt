package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CompetitionListFragment : Fragment() {

    private lateinit var contest_tit: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var contestList: ArrayList<Contest>
    private lateinit var contestAdapter: ContestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_competition_list, container, false)
        contest_tit = view.findViewById(R.id.contest_tit)
        recyclerView = view.findViewById(R.id.recyclerView)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contestList = ArrayList()
        contestAdapter = ContestAdapter(contestList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = contestAdapter

        // 클릭 리스너 설정
        contestAdapter.setOnItemClickListener { selectedContest ->
            val intent = Intent(requireContext(), ContestDetailedActivity::class.java)
            intent.putExtra("contest_url", selectedContest.url)
            startActivity(intent)
        }

        GlobalScope.launch(Dispatchers.IO) {
            val contestInfo = crawlcontestInfo()
            contestList.addAll(contestInfo.map { it ->
                val title = it.first
                val day = it.second
                val url = it.second

                Contest(title, day, url)
            })

            withContext(Dispatchers.Main) {
                contestAdapter.notifyDataSetChanged()
            }
        }
    }

    private val contestUrl = "https://www.wevity.com/?c=find&s=1&gub=2&cidx=5"

    private suspend fun crawlcontestInfo(): List<Pair<String, String>> {
        val contestInfo = ArrayList<Pair<String, String>>()

        try {
            val doc = Jsoup.connect(contestUrl).get()
            val titleElements  = doc.select(".tit a")
            val dayElements = doc.select(".day")

            for (i in titleElements.indices) {
                val titleText = titleElements[i].ownText()
                val dayText = dayElements[i+1].text()
                val url = titleElements[i].attr("href") // Get the href attribute of the 'a' tag
                contestList.add(Contest(titleText, dayText, url))

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return contestInfo
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompetitionListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompetitionListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



