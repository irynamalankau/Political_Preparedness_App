package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.Division
import org.koin.android.viewmodel.ext.android.viewModel

class VoterInfoFragment() : Fragment() {

    private val viewModel: VoterInfoViewModel by viewModel()
    private val args: VoterInfoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentVoterInfoBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_voter_info, container, false
        )

       binding.lifecycleOwner = this
        binding.viewModel = viewModel



        //Populate voter info
        val division = args.argDivision
        if (division.state.isEmpty()) {
            viewModel.getVoterInfo(args.argElectionId, division.country)
        } else {
            viewModel.getVoterInfo(args.argElectionId, "${division.country} - ${division.state}")
        }

        //TODO: Handle loading of URLs

        // Voting Locations
        viewModel.votingLocationUrl.observe(viewLifecycleOwner, Observer {
            it?.let {
                loadUrl(it)
                viewModel.votingLocationsNavigated()
            }
        })

        // Ballot Information
        viewModel.ballotInformationUrl.observe(viewLifecycleOwner, Observer {
            it?.let {
                loadUrl(it)
                viewModel.ballotInformationNavigated()
            }
        })

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
return binding.root
    }

    //Create method to load URL intents
    private fun loadUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}