package com.oguzhanturkmen.kotlinulkelerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzhanturkmen.kotlinulkelerapp.R
import com.oguzhanturkmen.kotlinulkelerapp.adapter.CountryAdapter
import com.oguzhanturkmen.kotlinulkelerapp.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        //viewmodelprovider hangi fragmenttayız veya hangi viewmodelin sınıfıyla çalışmak istiyoruz
        //onu söylüyoruz.
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        countryList.layoutManager = LinearLayoutManager(context)
        countryList.adapter = countryAdapter

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()

            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->

            countries?.let{
                countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }

        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it){
                    countryError.visibility = View.VISIBLE
                    countryList.visibility = View.GONE
                    countryLoading.visibility = View.GONE
                }else{
                    countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner,Observer { loading ->

            loading.let {
                if (it){
                    countryLoading.visibility = View.VISIBLE
                    countryList.visibility = View.GONE
                    countryError.visibility = View.GONE
                }else{
                    countryLoading.visibility = View.GONE
                }
            }

        })
    }
}