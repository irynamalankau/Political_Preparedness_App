package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import org.koin.android.viewmodel.ext.android.viewModel


class ElectionsFragment: Fragment() {

    //Declare ViewModel
    private val viewModel: ElectionsViewModel by viewModel()

    //Declare recycler adapter
    private lateinit var adapterUpcomingElections: ElectionListAdapter


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentElectionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_election, container, false
        )

        binding.viewModel = viewModel





        //Setup recycler adapters
        adapterUpcomingElections = ElectionListAdapter(ElectionListener {
            viewModel.onElectionClicked(it)
        })
        binding.recyclerUpcomingElections.adapter = adapterUpcomingElections

        //Populate recycler adapters
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterUpcomingElections.submitList(it)
            }
        })


        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, Observer { election->
            election?.let{
                this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
            }
            viewModel.onVoterInfoNavigated()
        })




        //TODO: Link elections to voter info

        return binding.root
    }

}