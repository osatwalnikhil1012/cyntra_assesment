package com.nikhilosatwal.cyntra_assesment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikhilosatwal.cyntra_assesment.R
import com.nikhilosatwal.cyntra_assesment.databinding.DeviceStatusItemBinding
import com.nikhilosatwal.cyntra_assesment.models.Device

class DeviceListAdapter(private val deviceList: List<Device>, private val context : Context, private val onItemClickListener : ItemClickListener) :
    RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    private lateinit var binding: DeviceStatusItemBinding

    class ViewHolder(private val binding : DeviceStatusItemBinding, private val context : Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(device : Device) {
            binding.device = device
            val deviceHealth = device.device_health
            val deviceHealthPrint = deviceHealth.printer
            val deviceHealthPayment = deviceHealth.payment
            binding.deviceName.text = "Device ${device.device_name}"
            if (deviceHealthPayment && deviceHealthPrint) {
                binding.statusIcon.setImageDrawable(context.getDrawable(R.drawable.green_tick_icon))
            } else if (!deviceHealthPayment && !deviceHealthPrint) {
                binding.statusIcon.setImageDrawable(context.getDrawable(R.drawable.red_cross_icon))
            } else {
                binding.statusIcon.setImageDrawable(context.getDrawable(R.drawable.exclamation_mark))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DeviceStatusItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,context)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = deviceList[position]
        holder.bind(device)
        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClick(device)
        }
    }

}