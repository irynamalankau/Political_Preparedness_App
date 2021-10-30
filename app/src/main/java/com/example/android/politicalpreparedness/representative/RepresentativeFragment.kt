package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.network.ApiStatus
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.bind
import java.util.Locale
import org.koin.android.viewmodel.ext.android.viewModel

class RepresentativeFragment : Fragment() {

    private lateinit var binding: FragmentRepresentativeBinding
    private val viewModel: RepresentativeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //Establish bindings
        binding = FragmentRepresentativeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Define and assign Representative adapter
        val representativeAdapter = RepresentativeListAdapter()
        binding.recyclerRepresentatives.adapter = representativeAdapter


        //Populate Representative adapter
        viewModel.representatives.observe(viewLifecycleOwner, Observer {
            it?.let {
                representativeAdapter.submitList(it)
            }
        })

        binding.state.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.states)
        )

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it==ApiStatus.ERROR)
                binding.listPlaceholder.visibility = View.VISIBLE
        })



        viewModel.messageResource.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })

        //Establish button listeners for field and location search
        binding.buttonSearch.setOnClickListener {
            hideKeyboard()
            viewModel.fetchRepresentatives()

        }

        binding.buttonLocation.setOnClickListener {
            if (checkLocationPermissions()) {
                getLocation()
            }
        }

        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), R.string.location_permission_needed, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
            )
            false
        }
    }

    private fun isPermissionGranted() : Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        )
        return true
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        locationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val address = geoCodeLocation(location)
                viewModel.address = address

                val states = resources.getStringArray(R.array.states)
                val selectedStateIndex = states.indexOf(address.state)

                with(binding){
                    addressLine1.setText(address.line1)
                    addressLine2.setText(address.line2)
                    city.setText(address.city)
                    zip.setText(address.zip)
                    state.setSelection(selectedStateIndex)
                }

                viewModel.fetchRepresentatives()
            }
        }.addOnFailureListener { e ->
            Log.e(TAG, e.message.toString())
            showToast(R.string.error_getting_location)
        }
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun showToast(messageResource: Int) {
        context?.apply {
            Toast.makeText(applicationContext, getString(messageResource), Toast.LENGTH_SHORT).show()
        }
    }



    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val TAG = "RepresentativesFragment"
    }

}