package com.example.newsapp

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.newsapp.adapter.TopListAdapter
import com.example.newsapp.manager.GetNewsManager
import com.example.newsapp.util.ProgressDialogUtil
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var mActivity : Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)

        val manager = GetNewsManager()
        ProgressDialogUtil.show(mActivity)
        // ニュースリストをとってきて表示する
        val listener = object : GetNewsManager.ICallback {
            override fun getFinished() {
                mActivity.runOnUiThread {
                    // メインスレッドで実行する処理
                    ProgressDialogUtil.hide()
                    val adapter = TopListAdapter(mActivity, manager.getArticles())
                    val listView: ListView = view.findViewById(R.id.top_list_view)
                    listView.adapter = adapter
                }
            }

        }
        thread {
            manager.getNews(listener)
        }


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TopFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}