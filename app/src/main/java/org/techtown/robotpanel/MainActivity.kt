package org.techtown.robotpanel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        door1Button.setOnClickListener { requestDoorControl("SMC-C02C-DOOR-001") }
        door2Button.setOnClickListener { requestDoorControl("SMC-C02C-DOOR-002") }
        door3Button.setOnClickListener { requestDoorControl("SMC-C02C-DOOR-003") }
        door4Button.setOnClickListener { requestDoorControl("SMC-C02C-DOOR-004") }
        door5Button.setOnClickListener { requestDoorControl("SMC-C02C-DOOR-005") }
        currentMapButton.setOnClickListener { requestCurrentMAp() }
        pointListButton.setOnClickListener { requestPointList() }
        pathListButton.setOnClickListener { requestPathList() }
        sequenceListButton.setOnClickListener { requestSequenceList() }
    }

    private fun requestCurrentMAp() {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.getCurrentMap(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<CurrentMapData> {
            override fun onResponse(call: Call<CurrentMapData>, response: Response<CurrentMapData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<CurrentMapData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestPointList() {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.getPointList(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<PointListData> {
            override fun onResponse(call: Call<PointListData>, response: Response<PointListData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
                requestPathList()
            }

            override fun onFailure(call: Call<PointListData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestPathList() {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.getPathList(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<PathListData> {
            override fun onResponse(call: Call<PathListData>, response: Response<PathListData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<PathListData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestSequenceList() {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.getSequenceList(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<SequenceListData> {
            override fun onResponse(call: Call<SequenceListData>, response: Response<SequenceListData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<SequenceListData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestSequenceCancel() {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.getSequenceCancel(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestNaviStart(location: String) {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.postNaviStart(
            requestCode = AppData.generateRequestCode(),
            locations = location
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestCruzeStart(path: String) {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.postCruzeStart(
            requestCode = AppData.generateRequestCode(),
            pathname = path,
            count = "1",
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestSequenceStart(sequence: String) {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.postSequenceStart(
            requestCode = AppData.generateRequestCode(),
            sequence = sequence
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestSequenceStartByName(name: String) {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.postSequenceStartByName(
            requestCode = AppData.generateRequestCode(),
            name = name
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestBumperRelease() {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        RobotClient.api.postBumperRelease(
            requestCode = AppData.generateRequestCode(),
        ).enqueue(object : Callback<RobotSimpleData> {
            override fun onResponse(call: Call<RobotSimpleData>, response: Response<RobotSimpleData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<RobotSimpleData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }

    private fun requestDoorControl(id: String) {
        val method = object {}.javaClass.enclosingMethod?.name
        AppData.debug(TAG, "$method called.")
        binding.resultTextView.text = "$method..."

        DoorClient.api.postDoorControl(
            requestCode = AppData.generateRequestCode(),
            source = "Robot Panel",
            target = id,
            command = "open"
        ).enqueue(object : Callback<DoorData> {
            override fun onResponse(call: Call<DoorData>, response: Response<DoorData>) {
                AppData.debug(TAG, "$method onResponse called.")
                response.body()?.apply {
                    binding.resultTextView.text = this.toString()
                }
            }

            override fun onFailure(call: Call<DoorData>, t: Throwable) {
                AppData.debug(TAG, "$method onFailure called : ${t.message}")
                binding.resultTextView.text = t.message
            }
        })
    }
}