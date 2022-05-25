package com.example.satpamtanggap.models

class Laporan {
    var email: String? = null
    var isi: String? = null
    var judul: String? = null
    var pengirim: String? = null
    var timestamp: String? = null

    constructor(){}
    constructor(email: String, isi: String, judul: String, timestamp: String, pengirim: String){
        this.email = email
        this.isi = isi
        this.judul = judul
        this.pengirim = pengirim
        this.timestamp = timestamp
    }
}