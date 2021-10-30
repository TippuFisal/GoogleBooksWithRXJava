package com.sheriff.rxjavawithmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sheriff.rxjavawithmvvm.network.BookListModel
import com.sheriff.rxjavawithmvvm.network.RetroInstance
import com.sheriff.rxjavawithmvvm.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BookListViewModel : ViewModel() {

    var bookList: MutableLiveData<BookListModel>

    init {
        bookList = MutableLiveData()
    }

    fun getBookListObserver(): MutableLiveData<BookListModel> {
        return bookList
    }

    fun makeApiCall(query: String) {
        val retroInstance = RetroInstance.getRetrofitInstance().create(RetroService::class.java)
        retroInstance.getBookListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }

    private fun getBookListObserverRx(): Observer<BookListModel> {
        return object : Observer<BookListModel> {
            override fun onSubscribe(d: Disposable) {
                // start showing the progress indicator
            }

            override fun onNext(bookListModel: BookListModel) {
                bookList.postValue(bookListModel)
            }

            override fun onError(e: Throwable) {
                bookList.postValue(null)
            }

            override fun onComplete() {
                // Hide the progress indicator
            }

        }
    }

}