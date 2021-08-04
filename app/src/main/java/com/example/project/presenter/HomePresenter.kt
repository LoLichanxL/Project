package com.example.project.presenter

import com.example.project.Contract
import com.example.project.MainActivity
import com.example.project.model.Advert
import com.example.project.model.database.Database
import java.util.*

class HomePresenter(val view: Contract.HomeView, val mainActivity: Contract.MainView) : Contract.HomePresenter{
    override fun onDatabaseIsUploadAdverts(list: List<HashMap<String, Object>>) {
        view.createAdvertsRecyclerView(list)
    }

    override fun onFragmentIsStarted() {
        Database.getAdverts(this)
    }

    override fun onAddAdvertButtonIsClicked() {
        mainActivity.openCreateAdvertFragment()
    }
}