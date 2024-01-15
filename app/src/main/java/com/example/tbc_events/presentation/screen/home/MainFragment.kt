package com.example.tbc_events.presentation.screen.home

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbc_events.R
import com.example.tbc_events.databinding.FragmentMainBinding
import com.example.tbc_events.presentation.event.home.ItemEvent
import com.example.tbc_events.presentation.screen.base.BaseFragment
import com.example.tbc_events.presentation.state.home.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainFragmentViewModel by viewModels()
    private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter

    override fun setUp() {
        bindObservers()
        setUpUsersRv()
        listeners()
    }

    private fun setUpUsersRv() {
        usersRecyclerAdapter = UsersRecyclerAdapter()
        binding.rvUsersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersRecyclerAdapter
        }
    }

    private fun listeners() {
        usersRecyclerAdapter.apply {
            itemOnClick = { id ->
                //this will trigger the navigation event and you will go to details screen
                viewModel.onEvent(ItemEvent.ItemClicked(id))
            }

            itemOnLongClick = { id ->
                //this one will make the item selected.
                viewModel.onEvent(ItemEvent.ItemOnLongClicked(id))
            }
        }

        with(binding) {
            btnDeleteUsers.setOnClickListener {
                //this will delete items. works for both fake api version and the other one.
                viewModel.onEvent(ItemEvent.ItemsDelete)
            }

            btnUserEvent.setOnClickListener {
                //this is just an example of User Event. It's a basic button click which still counts as an event.
                Toast.makeText(context, "This is a User Event", Toast.LENGTH_SHORT).show()
                btnUserEvent.setBackgroundColor(
                    requireContext().resources.getColor(
                        R.color.light_red,
                        requireContext().resources.newTheme()
                    )
                )
            }
        }
    }

    private fun navigateToDetails(id: Int) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(id))
    }

    //this function handles the state changes. it updates the progress bar visibility, submits data to recycler view
    // and if needed, shows a Toast message with specified error.
    private fun handleState(state: HomeState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.users?.let {
            usersRecyclerAdapter.setData(it)
        }

        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            //we reset error to null, because of the way data classes work.
            //if we don't update it to null, we will NOT get the recurrent errors.
            //for example, if error 1 happens and we don't update the error value to null, and then the error 1
            //happens again after a few seconds, we will NOT see the second error message.
            viewModel.onEvent(ItemEvent.ResetErrorMessage)
        }
    }

    //This function handles the uiEvent (Navigation Events in this case).
    private fun handleEvent(event: MainFragmentViewModel.NavigationEvent) {
        when (event) {
            is MainFragmentViewModel.NavigationEvent.NavigateToDetails -> {
                Toast.makeText(
                    context,
                    "This was a Navigation Event (ViewModel Event)",
                    Toast.LENGTH_LONG
                ).show()
                navigateToDetails(event.id)
            }
        }
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.homeState.collect {
                        handleState(it)
                    }
                }

                launch {
                    viewModel.uiEvent.collect {
                        handleEvent(it)
                    }
                }
            }
        }
    }
}