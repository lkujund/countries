package hr.algebra.countries.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.countries.R
import hr.algebra.countries.adapter.CountriesAdapter
import hr.algebra.countries.databinding.FragmentItemsBinding
import hr.algebra.countries.framework.fetchItems
import hr.algebra.countries.model.Country

class CountriesFragment : Fragment() {

    private lateinit var countries: MutableList<Country>
    private lateinit var binding: FragmentItemsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        countries = requireContext().fetchItems()
        binding = FragmentItemsBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var searchView: androidx.appcompat.widget.SearchView = view.findViewById(R.id.svSearch)

        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CountriesAdapter(requireContext(), countries)
        }
        var countriesAdapter = binding.rvItems.adapter as CountriesAdapter
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                countriesAdapter.filter(query)
                return true
            }
        })



    }

}