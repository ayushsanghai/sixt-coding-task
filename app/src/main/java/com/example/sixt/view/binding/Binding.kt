package com.example.sixt.view.binding

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.sixt.R


@BindingAdapter("carImage")
fun loadImage(view: ImageView, imageUrl: String?) {

    Glide.with(view.context)
        .load(imageUrl)
        .error(R.drawable.fall_back_image)
        .fallback(R.drawable.fall_back_image)
        .into(view)
}

@BindingAdapter("fuelLevelIndicator")
fun showFuelLevel(view: ProgressBar, fuelLevel: Double?) {
    if (fuelLevel != null) {
        view.secondaryProgress = (fuelLevel *100).toInt()
    }
}

@BindingAdapter("fuelLevelDisplay")
fun showFuelLevelDisplay(view: TextView, fuelLevel: Double?) {
    if (fuelLevel != null) {
        view.text = "${(fuelLevel *100).toInt()}%"
    }
}

@BindingAdapter("fuelTypeDisplay")
fun showFuelType(view: TextView, fuelType: String?) {
    view.let{
        when(fuelType){
            "D"->it.text = view.context.getString(R.string.diesel)
            "P"->it.text = view.context.getString(R.string.petrol)
            "E"->it.text = view.context.getString(R.string.electric)
        }
    }
}

@BindingAdapter("transmissionIcon")
fun showTransmissionIcon(view: ImageView, transmission: String?) {
    view.let{
        when(transmission){
            "M"->it.setImageResource(R.drawable.ic_manual_transmission)
            "A"->it.setImageResource(R.drawable.ic_automation_transmission)
        }
    }
}

@BindingAdapter("transmissionDisplay")
fun showTransmissionDisplay(view: TextView, transmission: String?) {
    view.let{
        when(transmission){
            "M"->it.text = view.context.getString(R.string.manual)
            "A"->it.text = view.context.getString(R.string.automatic)
        }
    }
}

@BindingAdapter("colorDisplay")
fun showColorDisplay(view: TextView, color: String?) {
    if (color != null) {
        view.text = color.replace("_"," ").uppercase()
    }
}

