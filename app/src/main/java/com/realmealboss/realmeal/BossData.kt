package com.realmealboss.realmeal

class BossData{
    companion object{
        private val TAG = BossData::class.java.simpleName
        private lateinit var id: String

        fun setBid(bossId:String){
            id = bossId
        }

        fun getBid() : String{
            return id
        }
    }
}