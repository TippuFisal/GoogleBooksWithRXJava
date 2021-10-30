package com.sheriff.rxjavawithmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.sheriff.rxjavawithmvvm.R
import com.sheriff.rxjavawithmvvm.network.BookListModel
import com.sheriff.rxjavawithmvvm.network.VolumeInfo

class BookListAdapter() : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    var bookListData = ArrayList<VolumeInfo>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<MaterialTextView>(R.id.tvTitle)
        val tvPublisher = itemView.findViewById<MaterialTextView>(R.id.tvPublisher)
        val tvDescription = itemView.findViewById<MaterialTextView>(R.id.tvDescription)
        val imgBookThumbnail = itemView.findViewById<AppCompatImageView>(R.id.imgBookThumbnail)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_book_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = bookListData[position].volumeInfo.title
        holder.tvPublisher.text = bookListData[position].volumeInfo.publisher
        holder.tvDescription.text = bookListData[position].volumeInfo.description
        val URL = bookListData[position].volumeInfo.imageLinks
        Glide.with(holder.imgBookThumbnail)
            .load(URL)
            .circleCrop()
            .into(holder.imgBookThumbnail)
    }

    override fun getItemCount(): Int = bookListData.size


}