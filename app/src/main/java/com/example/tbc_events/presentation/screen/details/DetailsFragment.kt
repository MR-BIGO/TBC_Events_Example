package com.example.tbc_events.presentation.screen.details

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.databinding.FragmentDetailsBinding
import com.example.tbc_events.presentation.screen.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsFragmentViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun setUp() {
        loadDetails()
        listeners()
        bindObservers()
    }

    private fun loadDetails() {
        viewModel.loadDetails(args.id)
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToMainFragment())
        }
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemFLow.collect {
                    when (it) {
                        is Resource.Success -> {
                            with(binding) {
                                tvEmail.text = it.data.email
                                tvFirstName.text = it.data.firstName
                                tvLastName.text = it.data.lastName
                                Glide.with(requireContext()).load(it.data.img).into(ivUserProfile)
                            }
                        }

                        is Resource.Error -> Toast.makeText(context, it.error, Toast.LENGTH_SHORT)
                            .show()

                        is Resource.Loading -> binding.progressBar.visibility =
                            if (it.loading) View.VISIBLE else View.GONE

                        else -> {}
                    }
                }
            }
        }
    }

}