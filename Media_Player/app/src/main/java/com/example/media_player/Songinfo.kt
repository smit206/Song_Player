package com.example.media_player

class Songinfo {

    var Title:String? = null
    var AuthorName:String? = null
    var SongURL:String? = null
    var BtnText:String? = "Start"

    constructor(Title:String,AuthorName:String,SongURL:String,BtnText:String){
        this.Title = Title
        this.AuthorName = AuthorName
        this.SongURL = SongURL
        this.BtnText = BtnText
    }

}