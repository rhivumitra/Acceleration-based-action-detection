package com.example.blackboxinvaders

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import java.io.File
import java.io.FileWriter


class SecondActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var my_accelerometer: Sensor
    private lateinit var tvX: TextView
    private lateinit var tvY: TextView
    private lateinit var tvZ: TextView

    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    private var accelerationData: SensorData? = null

//    data class SensorData(var x:Float, var y:Float, var z:Float, var timestamp:Long ) {
//        fun toCSVRow(): String{
//            return "${x},${y},${z},${timestamp}"
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        tvX = findViewById<TextView>(R.id.tvX)
        tvY = findViewById<TextView>(R.id.tvY)
        tvZ = findViewById<TextView>(R.id.tvZ)

        this.sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        my_accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        //val printer = writeAndSend()
        //printer.printToCSVFile("a,b,c,d")

        val getData = findViewById<Button>(R.id.btnGetData)
        getData.setOnClickListener {
            val intent = Intent(this, DataRecording::class.java)
            startActivity(intent)

            val sensorDataList = listOf(
                SensorData(1f, 2f, 3f, 123456),
                SensorData(4f, 5f, 6f, 654321)

            writeToCsvAndUploadToGCS(sensorDataList)
        }
    }
    override fun onResume(){
        super.onResume()
        sensorManager!!.registerListener(this, my_accelerometer, SensorManager.SENSOR_DELAY_GAME)
    } //end onResume

    //****************************************************************************************************************
    override fun onPause(){
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }//end onPause

    //*****************************************************************************************************************

//    private fun writeDataToCSV(data: String) {
//        val csvFile = "data.csv"
//        val file = File(Environment.getExternalStorageDirectory(), csvFile)
//        val writer = FileWriter(file)
//        for (row in data) {
//            writer.write(row + "\n")
//        }
//        writer.close()
//    }
//    class writeAndSend(){
//        fun printToCSVFile(dataList: String){
//
//            val file = File(Environment.getExternalStorageDirectory(), "collecteddata.csv")
//
//            val printWriter = file.printWriter()
//            printWriter.println("x,y,z,timestamp")
//
//            printWriter.flush()
//        }
//    }

    class DataPrinter(){
        fun printToCSVFile(datalist: List<SensorData>){
            //val file = File($"C:\\Users\\Yasamin\\AndroidStudioProjects\\BlackboxInvaders\\data\\RecordedData.csv")
            //val file = File("""C:\Users\Yasamin\Desktop\RecordedData.xlsx""")
            val file = File(System.getProperty("user.home"), "RecordedData.csv")
            //val printWriter = file.printWriter()


            val printWriter = file.printWriter()

            //column headers
            printWriter.println("x,y,z,timestamp")

            datalist.forEach {
                it -> printWriter.println(it.toCSVRow())
            }
            printWriter.flush()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {

        val timestamp = System.currentTimeMillis()
        val x = event!!.values[0]
        val y = event!!.values[1]
        val z = event!!.values[2]

        //accelerationData = SensorData(event!!.values[0], event!!.values[1], event!!.values[2], event!!.timestamp)
        accelerationData = SensorData(x, y, z, timestamp)
        //val csvRow = accelerationData!!.toCSVRow()
        //println(csvRow)

        val dataPrinter = DataPrinter()
        val dataList = listOf(SensorData(x,y,z,timestamp))

        //dataPrinter.printToCSVFile(datalist = dataList)



        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                tvX.text = "X: "+event!!.values[0]
                tvY.text = "Y: "+event!!.values[1]
                tvZ.text = "Z: "+event!!.values[2]
            }
        }

    }//end onSensorChanged


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //…
    }

//    private fun writeDataToCsv(fileName: String, dataList: List<SensorData>)
//    {

//        val writer = CSVWriter(FileWriter(fileName))
//        val header = arrayOf("x", "y", "z", "timestamp")
//        writer.writeNext(header)
//
//        for (data in dataList) {
//            val row = arrayOf(data.x.toString(), data.y.toString(), data.z.toString(), data.timestamp.toString())
//            writer.writeNext(row + "\n")
//        }
//
//        writer.close()
//    }

     fun writeToCsvAndUploadToGCS(data: List<SensorData>) {
        val file = File("collected.csv")
        val writer = FileWriter(file)

        writer.appendln("x,y,z,timestamp")

        for (item in data) {
            writer.appendln(item.toCSVRow())
        }
        writer.flush()
        writer.close()

        val credentials = GoogleCredentials.getApplicationDefault()
        val storage = StorageOptions.newBuilder().setCredentials(credentials).build().service
        val blobInfo = BlobInfo.newBuilder(bucketName, file.name).build()
        storage.create(blobInfo, file.readBytes())
    }


}
