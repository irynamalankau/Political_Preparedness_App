package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.ApiStatus
import org.koin.android.viewmodel.ext.android.viewModel


class ElectionsFragment: Fragment() {

    //Declare ViewModel
    private val viewModel: ElectionsViewModel by viewModel()

    //Declare recycler adapter
    private lateinit var adapterUpcomingElections: ElectionListAdapter
    private lateinit var adapterFollowedElections: ElectionListAdapter


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentElectionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_election, container, false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        //Setup recycler adapters
        //upcoming elections
        adapterUpcomingElections = ElectionListAdapter(ElectionListener {
            viewModel.onElectionClicked(it)
        })
        binding.recyclerUpcomingElections.adapter = adapterUpcomingElections

        //elections that user is following

        adapterFollowedElections = ElectionListAdapter(ElectionListener {
            viewModel.onElectionClicked(it)
        })
        binding.recyclerFollowedElections.adapter = adapterFollowedElections


        //Populate recycler adapters
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterUpcomingElections.submitList(it)
            }
        })

        viewModel.followedElections.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterFollowedElections.submitList(it)
            }
        })


        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, Observer {
            if (it){
                this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                        viewModel.clickedElection.id,
                        viewModel.clickedElection.division))
                viewModel.onVoterInfoNavigated()
            }
        })

        return binding.root
    }

}