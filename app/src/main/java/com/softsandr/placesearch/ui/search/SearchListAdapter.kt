package com.softsandr.placesearch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.softsandr.placesearch.BR
import com.softsandr.placesearch.R
import com.softsandr.placesearch.view.CheckableImageView

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
class SearchListAdapter(
    lifecycleOwner: LifecycleOwner,
    searchViewModel: SearchViewModel,
    private val clickCallback: (SearchListItem) -> Unit,
    private val saveCallback: (SearchListItem, CheckableImageView) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder>() {

    private val items = ArrayList<SearchListItem>()

    init {
        searchViewModel.getVenues().observe(lifecycleOwner, Observer { venues ->
            venues?.let {
                items.clear()
                items.addAll(venues.map { v -> SearchListItem.buildFromVenue(v) })
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return SearchItemViewHolder(binding, clickCallback, saveCallback)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.list_item_search
    }

    override fun getItemCount(): Int = items.size

    class SearchItemViewHolder(private val binding: ViewDataBinding,
                               private val clickCallback: (SearchListItem) -> Unit,
                               private val saveCallback: (SearchListItem, CheckableImageView) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchListItem) {
            binding.root.setOnClickListener { clickCallback.invoke(item) }
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            val saveView = binding.root.findViewById<CheckableImageView>(R.id.list_item_search_star_iv)
            saveView.setOnClickListener { saveCallback.invoke(item, saveView) }
        }
    }
}