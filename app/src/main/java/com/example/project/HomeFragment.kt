package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.adapter.AdvertsAdapter
import com.example.project.databinding.FragmentHomeBinding
import com.example.project.model.Advert
import com.example.project.presenter.HomePresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class HomeFragment : Fragment(), Contract.HomeView {
    lateinit var presenter:HomePresenter
    lateinit var recyclerView:RecyclerView
    lateinit var binding:FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        presenter = HomePresenter(this, activity as MainActivity)
        recyclerView = view.findViewById<RecyclerView>(R.id.adverts_recycler_view)
        view.findViewById<FloatingActionButton>(R.id.create_advert_floating_action_button).setOnClickListener(View.OnClickListener {
            presenter.onFloatingBarIsClicked()
        })
        presenter.onFragmentIsStarted()
        return view
    }

    override fun createAdvertsRecyclerView(list: List<HashMap<String, Object>>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AdvertsAdapter(list, activity as MainActivity)
    }

    fun updateAdapter(){
        recyclerView.adapter?.notifyDataSetChanged()
    }
}