package com.example.sixt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sixt.databinding.FragmentCarListBinding
import com.example.sixt.databinding.LayoutLoadingScreenBinding
import com.example.sixt.view.adapters.CarAdapter
import com.example.sixt.viewmodel.CarViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CarListFragment : Fragment() {

    private lateinit var _binding: FragmentCarListBinding
    private lateinit var carAdapter:CarAdapter
    private val carViewModel:CarViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        LayoutLoadingScreenBinding.inflate(inflater,container,false)

        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        _binding.isLoading = true
        observeViewModel()
        setUpCarAdapter()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.isLoading = false
    }

    //observe view model live data
    private fun observeViewModel() {
        //observe car list live data and submit list to recycler view adapter
        carViewModel.carList.observe(viewLifecycleOwner) {
            carAdapter.submitList(it)
        }
    }

    //attach adapter to car list recyclerview
    private fun setUpCarAdapter() {
        carAdapter=CarAdapter()
        _binding.carListViewRv.adapter=carAdapter
    }

}