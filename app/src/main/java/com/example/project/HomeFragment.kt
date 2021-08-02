package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.adapter.AdvertsAdapter
import com.example.project.model.Advert
import com.example.project.presenter.HomePresenter
import java.util.*

class HomeFragment : Fragment(), Contract.HomeView {
    val presenter = HomePresenter(this)
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.averts_recycler_view)
        presenter.onFragmentIsStarted()
        return view
    }

    override fun createAdvertsRecyclerView(list: List<HashMap<String, Object>>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AdvertsAdapter(list)
    }

    companion object {

    }
}