package com.example.inventory.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.*
import com.example.inventory.databinding.ItemListFragmentBinding
import com.example.inventory.ui.adapters.ItemListAdapter
import com.example.inventory.viewmodel.InventoryViewModel
import com.example.inventory.viewmodel.InventoryViewModelFactory

class ItemListFragment : Fragment() {
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private var _binding: ItemListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ItemListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemListAdapter { id ->
            val action =
                ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(id)
            findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        viewModel.allItems.observe(viewLifecycleOwner) { items -> adapter.submitList(items) }

        binding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
                getString(R.string.add_fragment_title)
            )
            findNavController().navigate(action)
        }
    }
}