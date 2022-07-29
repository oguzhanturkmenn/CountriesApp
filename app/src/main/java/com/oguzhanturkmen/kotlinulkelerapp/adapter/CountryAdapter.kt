package com.oguzhanturkmen.kotlinulkelerapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanturkmen.kotlinulkelerapp.R
import com.oguzhanturkmen.kotlinulkelerapp.databinding.ItemCountryBinding
import com.oguzhanturkmen.kotlinulkelerapp.model.Country
import com.oguzhanturkmen.kotlinulkelerapp.util.downloadFromUrl
import com.oguzhanturkmen.kotlinulkelerapp.util.placeHolderProgressBar
import com.oguzhanturkmen.kotlinulkelerapp.view.CountryFragment
import com.oguzhanturkmen.kotlinulkelerapp.view.CountryFragmentDirections
import com.oguzhanturkmen.kotlinulkelerapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.fragment_feed.view.*
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener{



    class CountryViewHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCountryClicked(v: View) {

        val uuid = v.countryUuidText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
        action.countryUuid = uuid

        Navigation.findNavController(v).navigate(action)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country,parent,false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.country = countryList[position]
        holder.view.listener = this



/*
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion
        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()

            //action.countryUuid
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.view.context))

 */

    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }




}