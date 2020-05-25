package com.realmealboss.realmeal

class BossData{
    companion object{
        private val TAG = BossData::class.java.simpleName
        private lateinit var objectId: String
        private lateinit var bossId: String
        private lateinit var name: String
        private lateinit var restaurantOid: String

        fun setOid(id:String){
            objectId = id
        }
        fun getOid() : String{
            return objectId
        }

        fun setBid(id:String){
            bossId = id
        }
        fun getBid() : String{
            return bossId
        }

        fun setROid(id:String){
            restaurantOid = id
        }
        fun getROid() : String{
            return restaurantOid
        }

    }
}