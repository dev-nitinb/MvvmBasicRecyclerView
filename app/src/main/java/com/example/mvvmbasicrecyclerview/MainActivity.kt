package com.example.mvvmbasicrecyclerview

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmbasicrecyclerview.adapter.RecyclerViewAdapter
import com.example.mvvmbasicrecyclerview.model.User
import com.example.mvvmbasicrecyclerview.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var fabButton: AppCompatImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerViewAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabButton = findViewById<AppCompatImageView>(R.id.image_view_add)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        //create instance of MainActivityViewModel
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        //implement observers for MainActivityViewModel
        mainActivityViewModel.getUsers().observe(this, Observer<ArrayList<User>>{
            //notify change to adapter
            mAdapter.notifyDataSetChanged()
        })

        mainActivityViewModel.getIsUpdating()!!.observe(this, Observer<Boolean>{
            //code to hide or show the progress bar
            if(it){
                showProgressBar()
            }
            else{
                hideProgressBar()
                recyclerView.smoothScrollToPosition(mainActivityViewModel.getUsers().value!!.size-1)
            }
        })

        fabButton.setOnClickListener {
            mainActivityViewModel.addNewValue(User("ZZZ"))
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = RecyclerViewAdapter( mainActivityViewModel.getUsers().value!!)
        val linearLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

}
