package com.sheriff.rxjavawithmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.sheriff.rxjavawithmvvm.adapter.BookListAdapter
import com.sheriff.rxjavawithmvvm.network.BookListModel
import com.sheriff.rxjavawithmvvm.viewmodel.BookListViewModel

class BookListActivity : AppCompatActivity() {

    private lateinit var bookListViewModel: BookListViewModel
    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var etSearchBook: EditText
    private lateinit var rvBookList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    fun initUI(){
        initViews()
        initSearchBox()
        initRecyclerView()
    }

    private fun initViews(){
        etSearchBook = findViewById<EditText>(R.id.etSearch)
        rvBookList = findViewById<RecyclerView>(R.id.rvBookList)
    }

    fun initSearchBox(){
        etSearchBook.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    fun initRecyclerView(){
        rvBookList.apply {
            val linearLayoutManager = LinearLayoutManager(this@BookListActivity)
            val decoration = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(decoration)
            bookListAdapter = BookListAdapter()
            layoutManager = linearLayoutManager
            adapter = bookListAdapter
        }
    }

    fun loadAPIData(userInput: String){
        bookListViewModel = ViewModelProvider(this).get(BookListViewModel::class.java)
        bookListViewModel.getBookListObserver().observe(this, Observer<BookListModel>{
            when{
                it != null -> {
                    bookListAdapter.bookListData = it.items
                    bookListAdapter.notifyDataSetChanged()
                }
                else -> {
                    Toast.makeText(this, "Error in fetching the data", Toast.LENGTH_SHORT).show()
                }
            }
        })
        bookListViewModel.makeApiCall(userInput)
    }
}
