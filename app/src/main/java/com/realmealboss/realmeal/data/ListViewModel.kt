package com.realmealboss.realmeal.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel(){
    // MemoData의 MutableList를 저장하는 속성
    private val memos: MutableList<MemoData> = mutableListOf()
    // MutableList를 담을 MutableLiveData를 추가 (lazy로 지연 초기화)
    val memoLiveData: MutableLiveData<MutableList<MemoData>> by lazy {
        MutableLiveData<MutableList<MemoData>>().apply {
            value = memos
        }
    }
    // MemoData 객체를 리스트에 추가하고 MutableLiveData의 value를 갱신하여 Observer를 호출하도록 하는 함수
    fun addMemo(data: MemoData){
        val tempList = memoLiveData.value
        tempList?.add(data)
        memoLiveData.value = tempList
    }
}