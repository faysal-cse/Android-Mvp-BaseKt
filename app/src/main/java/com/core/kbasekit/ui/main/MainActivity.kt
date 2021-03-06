package com.core.kbasekit.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.core.kbasekit.R
import com.core.kbasekit.data.db.user.User
import com.core.kbasekit.event.BaseEvent
import com.core.kbasekit.ui.base.BaseActivity
import com.core.kbasekit.util.lib.BusProvider
import com.squareup.otto.Subscribe
import kotlinx.coroutines.experimental.selects.select
import org.jetbrains.anko.*


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 1/2/2018 at 1:19 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Azizul Islam on 1/2/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class MainActivity : BaseActivity<MainMvpView, MainPresenter>(), MainMvpView {

    lateinit var insertButton : Button
    lateinit var eventBusButton : Button
    lateinit var deleteButton : Button
    lateinit var recyclerView : RecyclerView
    lateinit var mainAdapter : MainAdapter

    override fun getPresenter(): MainPresenter {
        return MainPresenter(this)
    }
    override val getLayoutId: Int
        get() = R.layout.activity_main

    override val getMvpView: MainMvpView
        get() = this


    override fun startUi() {
        insertButton = findViewById(R.id.insert_button)
        eventBusButton = findViewById(R.id.event_bus)
        deleteButton = findViewById(R.id.delete)
        recyclerView = findViewById(R.id.recyclerView)
        mainAdapter = MainAdapter(this)
        insertButton.setOnClickListener(this)
        eventBusButton.setOnClickListener(this)
        deleteButton.setOnClickListener(this)
        initRecyclerView()
    }


    private fun initRecyclerView(){
        recyclerView.adapter = mainAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override val getMenuId: Int
        get() = -1


    override fun onClick(v: View?) {
        if(v?.id == R.id.insert_button) {
            presenter?.insertUser()
            //showAlert()
        }else if(v?.id == R.id.event_bus){
            var event = BaseEvent()
            event.name = "Event message"
            BusProvider.getBus().post(event)
        }else if(v?.id == R.id.delete){
            deleteUser()
        }
    }

    private fun deleteUser(){
        var users = mainAdapter.getItems()
        presenter?.deleteUsers(users)
    }

    override fun onUserFound(users : List<User>) {
       // Toast.makeText(this,"User List = ${users.size}",Toast.LENGTH_LONG).show()
        mainAdapter.clear()
        mainAdapter.addItems(users)
    }

    override fun onDbPrepare() {
        presenter?.getUsers()
    }

    override fun onResume() {
        super.onResume()
        BusProvider.getBus().register(this)
    }

    override fun onPause() {
        super.onPause()
        BusProvider.getBus().unregister(this)
    }

    /**
     * Event bus event receiver method
     */


    @Subscribe
    fun receiveEventMessage(baseEvent : BaseEvent){
        Toast.makeText(this,"Name = "+baseEvent.name,Toast.LENGTH_LONG).show()
    }

    @Subscribe
    fun receiveEventMessage2(baseEvent : BaseEvent){
        Toast.makeText(this,"Name 2 = "+baseEvent.name,Toast.LENGTH_LONG).show()
    }


    /**
     * Broadcast receiver
     */

    var broadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

        }
    }

}