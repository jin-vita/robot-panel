package org.techtown.robotpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.techtown.robotpanel.data.*
import org.techtown.robotpanel.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    companion object {
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() = with(binding) {
        doorControlButton.setOnClickListener { requestDoorControl("SMC-C02C-DOOR-001") }
        currentMapButton.setOnClickListener { requestCurrentMAp() }
        pointListButton.setOnClickListener { requestPointList() }
        pathListButton.setOnClickListener { requestPathList() }
        sequenceListButton.setOnClickListener { requestSequenceList() }
    }

    private fun requestCurrentMAp() {
        AppData.debug(TAG, "requestCurrentMAp called.")

        RobotClient.api.getCurrentMap(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<CurrentMapData> {
            override fun onResponse(call: Call<CurrentMapData>, response: Response<CurrentMapData>) {
                AppData.debug(TAG, "requestCurrentMAp onResponse called.")
                response.body()?.apply {  }
                requestPathList()
            }

            override fun onFailure(call: Call<CurrentMapData>, t: Throwable) {
                AppData.debug(TAG, "requestCurrentMAp onFailure called : ${t.message}")
            }
        })
    }

    private fun requestPointList() {
        AppData.debug(TAG, "requestPointList called.")

        RobotClient.api.getPointList(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<PointListData> {
            override fun onResponse(call: Call<PointListData>, response: Response<PointListData>) {
                AppData.debug(TAG, "requestPointList onResponse called.")
                response.body()?.apply {  }
                requestPathList()
            }

            override fun onFailure(call: Call<PointListData>, t: Throwable) {
                AppData.debug(TAG, "requestPointList onFailure called : ${t.message}")
            }
        })
    }
    
    private fun requestPathList() {
        AppData.debug(TAG, "requestPointList called.")

        RobotClient.api.getPathList(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<PathListData> {
            override fun onResponse(call: Call<PathListData>, response: Response<PathListData>) {
                AppData.debug(TAG, "requestPathList onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<PathListData>, t: Throwable) {
                AppData.debug(TAG, "requestPathList onFailure called : ${t.message}")
            }
        })
    }
    
    private fun requestSequenceList() {
        AppData.debug(TAG, "requestPointList called.")

        RobotClient.api.getSequenceList(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<SequenceListData> {
            override fun onResponse(call: Call<SequenceListData>, response: Response<SequenceListData>) {
                AppData.debug(TAG, "requestSequenceList onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<SequenceListData>, t: Throwable) {
                AppData.debug(TAG, "requestSequenceList onFailure called : ${t.message}")
            }
        })
    }
    
    private fun requestSequenceCancel() {
        AppData.debug(TAG, "requestPointList called.")

        RobotClient.api.getSequenceCancel(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "requestPathList onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "requestPathList onFailure called : ${t.message}")
            }
        })
    }
    
    private fun requestNaviStart(location: String) {
        AppData.debug(TAG, "requestStart called. $location")

        RobotClient.api.postNaviStart(
            requestCode = AppData.generateRequestCode(),
            locations = location
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "requestNaviStart onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "requestNaviStart onFailure called : ${t.message}")
            }
        })
    }
    
    private fun requestCruzeStart(path: String) {
        AppData.debug(TAG, "requestStart called. $path")

        RobotClient.api.postCruzeStart(
            requestCode = AppData.generateRequestCode(),
            pathname = path,
            count = "1",
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "requestCruzeStart onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "requestCruzeStart onFailure called : ${t.message}")
            }
        })
    }
    
    private fun requestSequenceStart(sequence: String) {
        AppData.debug(TAG, "requestStart called. $sequence")

        RobotClient.api.postSequenceStart(
            requestCode = AppData.generateRequestCode(),
            sequence = sequence
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "requestSequenceStart onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "requestSequenceStart onFailure called : ${t.message}")
            }
        })
    }

    private fun requestSequenceStartByName(name: String) {
        AppData.debug(TAG, "requestStart called. $name")

        RobotClient.api.postSequenceStartByName(
            requestCode = AppData.generateRequestCode(),
            name = name
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "requestSequenceStartByName onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "requestSequenceStartByName onFailure called : ${t.message}")
            }
        })
    }
    
    private fun requestBumperRelease() {
        AppData.debug(TAG, "requestBumperRelease called.")

        RobotClient.api.postBumperRelease(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "requestBumperRelease onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "requestBumperRelease onFailure called : ${t.message}")
            }
        })
    }

    private fun requestDoorControl(id: String) {
        AppData.debug(TAG, "requestDoorControl called. $id")

        DoorClient.api.postDoorControl(
            requestCode = AppData.generateRequestCode(),
            source = "Robot Panel",
            target = id,
            command = "open"
        ).enqueue(object : Callback<DoorData> {
            override fun onResponse(call: Call<DoorData>, response: Response<DoorData>) {
                AppData.debug(TAG, "requestDoorControl onResponse called.")
                response.body()?.apply {  }
            }

            override fun onFailure(call: Call<DoorData>, t: Throwable) {
                AppData.debug(TAG, "requestDoorControl onFailure called : ${t.message}")
            }
        })
    }
}