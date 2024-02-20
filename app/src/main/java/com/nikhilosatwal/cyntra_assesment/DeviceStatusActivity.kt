package com.nikhilosatwal.cyntra_assesment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.nikhilosatwal.cyntra_assesment.adapter.DeviceListAdapter
import com.nikhilosatwal.cyntra_assesment.adapter.ItemClickListener
import com.nikhilosatwal.cyntra_assesment.databinding.ActivityDeviceStatusBinding
import com.nikhilosatwal.cyntra_assesment.models.Device
import com.nikhilosatwal.cyntra_assesment.viewmodel.DeviceStatusViewModel
import javax.inject.Inject

class DeviceStatusActivity : AppCompatActivity(), ItemClickListener{

    private lateinit var binding : ActivityDeviceStatusBinding

    @Inject
    lateinit var viewModel: DeviceStatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_device_status)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        (applicationContext as MyApplication).appComponent.inject(this)
        viewModel.devicesLiveData.observe(this) { devices ->
            var workingDevice = 0
            var notWorkingDevice = 0
            var slowingDownDevice = 0
            devices.forEach { device ->
                val deviceHealth = device.device_health
                val deviceHealthPrint = deviceHealth.printer
                val deviceHealthPayment = deviceHealth.payment
                if (deviceHealthPayment && deviceHealthPrint) {
                    workingDevice++
                } else if (!deviceHealthPayment && !deviceHealthPrint) {
                    notWorkingDevice++
                } else {
                    slowingDownDevice++
                }
            }
            binding.deviceWorking.text = "${workingDevice} Devices"
            binding.deviceNotworking.text = "${notWorkingDevice} Devices"
            binding.deviceServerdown.text = "${slowingDownDevice} Devices"
            val recyclerView = binding.deviceList
            val adapter = DeviceListAdapter(devices, this, this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        }
        viewModel.loadDevicesFromJson()
        binding.imageView1.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.imageView1)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.item_one -> {
                        val sharedPreference =  getSharedPreferences("LOGIN_CRED", Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.clear()
                        editor.remove("username")
                        editor.remove("password")
                        editor.apply()
                        finish()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.show()
        }
    }

    override fun onItemClick(device: Device) {
        val builder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_dialog,null)
        val button = view.findViewById<ImageView>(R.id.close_icon)
        val titleText = view.findViewById<TextView>(R.id.title_name)
        titleText.text = "Device ${device.device_name}"
        builder.setView(view)
        button.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }
}