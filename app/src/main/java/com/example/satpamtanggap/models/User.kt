package com.example.satpamtanggap.models

import java.io.Serializable

class User: Serializable{
    var nama: String? = null
    var image:String? = null
    var alamat: String? = null
    var token: String? = null
    var id: String? = null

    constructor(){}

    constructor(nama: String?, image:String?, alamat: String?, id: String?, token: String?){
        this.nama = nama
        this.image = image
        this.alamat = alamat
        this. token = token
        this.id = id
    }

}