package com.example.media_player

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    var listSongs = ArrayList<Songinfo>()
    var adapter:MySongAdapter? = null
    var snglist:ListView? = null
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val PERMISSION_REQ_CODE = 100
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        snglist = findViewById(R.id.lssListSongs)
//        LoadURLOnline()
        CheckUserPermission()
//        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_AUDIO)!= PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                showPermissionDialog()
//            }
//            else{
//                LoadSng()
//            }
//        btn!!.setOnClickListener(View.OnClickListener {
//            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_AUDIO)!= PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                showPermissionDialog()
//            }
//            else{
//                LoadSng()
//            }
//
//        })



//        var mytracking = mySongTrack()
//        mytracking.start()
    }

    override fun onResume(){
        super.onResume()

        if(Build.VERSION.SDK_INT >= 32){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED){
                LoadSng()
            }
        }
    }

    fun LoadURLOnline(){
//        listSongs.add(Songinfo("1","idk","https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"))
//        listSongs.add(Songinfo("2","idk","https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"))
//        listSongs.add(Songinfo("3","idk","https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"))
//        listSongs.add(Songinfo("4","idk","https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"))
    }

    inner class MySongAdapter:BaseAdapter{
        var myListSong = ArrayList<Songinfo>()
        private var mp:MediaPlayer? = null

        constructor(myListSong:ArrayList<Songinfo>):super(){
            this.myListSong = myListSong
        }

        private var currentPlayingPosition:Int? = null

        override fun getCount(): Int {
            return this.myListSong.size
        }

        override fun getItem(p0: Int): Any {
            return this.myListSong[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.song_ticket,null)
            val Song = this.myListSong[p0]

            myView.findViewById<TextView>(R.id.tvSongName).text = Song.Title
            myView.findViewById<TextView>(R.id.tvAuthorName).text = Song.AuthorName
            myView.findViewById<Button>(R.id.buPlay).text = Song.BtnText
            myView.findViewById<Button>(R.id.buPlay).setOnClickListener(View.OnClickListener {

                if(mp!=null && mp!!.isPlaying){

                    mp!!.stop()
                    mp = MediaPlayer()
                    try {
                        mp!!.setDataSource(Song.SongURL)
                        mp!!.prepare()
                        mp!!.start()
//                        myView.findViewById<Button>(R.id.buPlay).setText("Start")

                        for(i in 0.. myListSong.size-1){
                            myListSong[i].BtnText = "Start"
                        }
                        this.myListSong[p0].BtnText = "Stop"
                        if (myView.findViewById<Button>(R.id.buPlay).text.equals("Stop")){
                            mp!!.stop()
                            mp!!.prepare()
                            this.myListSong[p0].BtnText = "Start"
                        }
                        notifyDataSetChanged()
                    }catch (ex:Exception){
                        ex.printStackTrace()
                    }
//                        myView.findViewById<Button>(R.id.buPlay).setText("Start")
//                    }
                }else{
                    mp = MediaPlayer()
                    try {
                        mp!!.setDataSource(Song.SongURL)
                        mp!!.prepare()
                        mp!!.start()
//                        myView.findViewById<Button>(R.id.buPlay).setText("Stop")

                        for(i in 0.. myListSong.size-1){
                            myListSong[i].BtnText = "Start"
                        }
                        this.myListSong[p0].BtnText = "Stop"
                        notifyDataSetChanged()
                    }catch (ex:Exception){
                        ex.printStackTrace()
                    }
                }

            })
            return myView
        }

    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun CheckUserPermission(){

        if(Build.VERSION.SDK_INT >= 32){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO),REQUEST_CODE_ASK_PERMISSION)
            }
            else{
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO),REQUEST_CODE_ASK_PERMISSION
                )
            }
        }
        else{
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE_ASK_PERMISSION)
            }else{
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE_ASK_PERMISSION
                )
            }
        }
    }
//            if(shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_AUDIO)){
//                AlertDialog.Builder(this)
//                    .setTitle("Permission Needed")
//                    .setMessage("This app needs permission to access audio files for loading songs.")
//                    .setPositiveButton("OK") {_,_->
//                        requestPermissions(
//                            arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO),REQUEST_CODE_ASK_PERMISSION
//                        )
//                    }
//                    .setNegativeButton("Cancel"){dialog,_ ->
//                        dialog.dismiss()
//                    }
//                    .create()
//                    .show()
//            }

//            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED){
//                LoadSng()
//            }else if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_MEDIA_AUDIO)){
//                showPermissionDialog()
//            }else{
//                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO), PERMISSION_REQ_CODE)
//            }


//            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO),REQUEST_CODE_ASK_PERMISSION)
//
//            }



//            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()
//                LoadSng()
//            }else if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
//                showPermissionDialog()
//            }else{
//                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQ_CODE)
//            }
//            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE_ASK_PERMISSION)
//            }



    private  val REQUEST_CODE_ASK_PERMISSION = 123

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_ASK_PERMISSION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    LoadSng()
                }else {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_MEDIA_AUDIO) == true){
                        showPermissionDialog()
                        Toast.makeText(this@MainActivity,"This is rational permission.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @SuppressLint("Range", "SuspiciousIndentation")
    fun LoadSng(){
        val allSongsURL = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val cursor = contentResolver.query(allSongsURL,null,selection,null,null)

            if(cursor!=null){
                if(cursor!!.moveToFirst()){
                    do{
                        val songURL = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))

                        if(!isSongAlreadyInList(songURL)){
                            val SongAuthor = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                            val SongName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                            listSongs.add(Songinfo(SongName,SongAuthor,songURL,"Start"))
                        }

                    }while (cursor!!.moveToNext())
                    cursor!!.close()
                    adapter = MySongAdapter(listSongs)
                    snglist!!.adapter = adapter
                }
        }
    }


    private fun isSongAlreadyInList(songURL:String):Boolean{
        for(song in listSongs){
            if(song.SongURL == songURL){
                return true
            }
        }
        return false
    }

    fun showPermissionDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permission Required")
        builder.setMessage("Some permission are needed to be allowed to use this feature.")
        builder.setPositiveButton("Grant"){d,_ ->
            d.cancel()
            startActivity(Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName")))
        }
        builder.setNegativeButton("Cancel"){d,_ ->
            d.dismiss()
        }
        builder.show()
    }
}
