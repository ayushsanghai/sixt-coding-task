package com.example.sixt.view


import android.animation.ValueAnimator
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sixt.R
import com.example.sixt.databinding.FragmentMapViewBinding
import com.example.sixt.viewmodel.CarViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLngBounds.Builder


class MapViewFragment : Fragment(), OnMapReadyCallback {

    private lateinit var _binding: FragmentMapViewBinding
    private lateinit var carImageAnimator: ValueAnimator
    private lateinit var cardAnimator: ValueAnimator
    private lateinit var myMap: GoogleMap
    private val carViewModel: CarViewModel by activityViewModels()
    private var isExpanded: Boolean = false
    private val markerList = mutableListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMapViewBinding.inflate(inflater, container, false)
        _binding.lifecycleOwner = this
        initializeUi()
        observeViewModel()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize Map
        val mapView = _binding.mapView2
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    //Set map style and add markers when map is ready
    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this.requireContext(), R.raw.style_json
                )
            )
            if (!success) {
                Log.e("Google Maps", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("Google Maps", "Can't find style. Error: ", e)
        }
        // Load markers on the map
        carViewModel.carList.observe(viewLifecycleOwner) {
            val builder = Builder()

            for (car in it) {
                if (car.latitude != null && car.longitude != null) {
                    try {
                        val lat = car.latitude.replaceFirst(Regex("\\.\\.d+"), "").toDouble()
                        val lng = car.longitude.replaceFirst(Regex("\\.\\.d+"), "").toDouble()
                        addMarker(car.id.toString(), LatLng(lat, lng), builder)
                    } catch (e: java.lang.Exception) {
                        Log.e("Marker Location", "Cannot parse latitude and longitude", e)
                    }
                }
            }

            // Calculate bounds and Set the camera position
            val bounds = builder.build()
            val padding = 100 // padding in pixels
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            myMap.moveCamera(cameraUpdate)
            setMarkerClickListener()
        }
    }

    //Initialize UI components and attach required animations
    private fun initializeUi() {
        _binding.carDetailCard.card.visibility = View.GONE
        _binding.carDetailCard.cardExpandIndicator.visibility = View.GONE
        setUpAnimator()
        setCardOnClickListener()
    }

    //Observe view model livedata
    private fun observeViewModel() {
        //observe live data for selected car update
        carViewModel.selectedCar.observe(viewLifecycleOwner) {
            _binding.carDetailCard.carModel = it
        }

    }

    //Add marker on the map at the provided location
    private fun addMarker(carID: String, carLocation: LatLng, builder: Builder) {
        val marker = myMap.addMarker(
            MarkerOptions()
                .position(carLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_default))
        )
        if (marker != null) {
            marker.tag = carID
            markerList.add(marker)
            builder.include(marker.position)
        }

    }

    private fun setMarkerClickListener() {

        myMap.setOnMarkerClickListener { marker ->
            _binding.carDetailCard.card.visibility = View.VISIBLE
            _binding.carDetailCard.cardExpandIndicator.visibility = View.VISIBLE
            carViewModel.setSelectedCar(marker.tag.toString())

            // Set selected marker color
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_selected))
            // Reset the color of all other markers
            for (m in markerList) {
                if (m != marker) {
                    m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_default))
                }
            }

            //move camera to center of the selected marker
            val center = CameraUpdateFactory.newLatLngZoom(marker.position, 15f)
            myMap.animateCamera(center)
            true
        }
    }

    private fun setCardOnClickListener() {

        val cardExpansionClickListener = View.OnClickListener {
            if (isExpanded) {
                _binding.carDetailCard.let {
                    it.carImage.animate().translationY(dpToPixel(0).toFloat()).duration = 500
                    it.collapsedLayoutView.animate().alpha(1.0f).duration = 800
                    it.expandedLayoutView.animate().alpha(0.0f).duration = 200
                    it.cardExpandIndicator.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                }
                carImageAnimator.reverse()
                cardAnimator.reverse()

            } else {
                _binding.carDetailCard.let {
                    it.carImage.animate().translationY(dpToPixel(-104).toFloat()).duration = 500
                    it.collapsedLayoutView.animate().alpha(0.0f).duration = 200
                    it.expandedLayoutView.animate().alpha(1.0f).duration = 800
                    it.cardExpandIndicator.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)

                }
                carImageAnimator.start()
                cardAnimator.start()
            }
            isExpanded = !isExpanded
        }

        _binding.carDetailCard.card.setOnClickListener(cardExpansionClickListener)
        _binding.carDetailCard.cardExpandIndicator.setOnClickListener(cardExpansionClickListener)
    }

    private fun setUpAnimator() {
        cardAnimator = ValueAnimator.ofInt(dpToPixel(160), dpToPixel(360))
        cardAnimator.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = _binding.carDetailCard.card.layoutParams
            layoutParams.height = `val`
            _binding.carDetailCard.card.layoutParams = layoutParams
        }
        cardAnimator.duration = 500

        carImageAnimator = ValueAnimator.ofInt(dpToPixel(200), dpToPixel(360))
        carImageAnimator.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = _binding.carDetailCard.carImage.layoutParams
            layoutParams.width = `val`
            _binding.carDetailCard.carImage.layoutParams = layoutParams
        }
        carImageAnimator.duration = 500
    }

    private fun dpToPixel(valueInDp: Int): Int {
        val density = resources.displayMetrics.density
        return (valueInDp * density + 0.5f).toInt()
    }


}