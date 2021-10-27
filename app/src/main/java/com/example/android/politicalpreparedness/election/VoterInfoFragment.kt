package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DateFormat

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


        viewModel.election.observe(viewLifecycleOwner, Observer {
            binding.electionDate.text = DateFormat.getDateInstance().format(it.electionDay)
        })

        Log.d(TAG, args.argElectionId.toString())

        viewModel.getVoterInfo(args.argDivision, args.argElectionId)

        //TODO: Handle loading of URLs

        // Voting Locations
        viewModel.url.observe(viewLifecycleOwner, Observer {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        })





        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
return binding.root
    }


    companion object {
        private const val TAG = "VoterInfoFragment"
    }

}

