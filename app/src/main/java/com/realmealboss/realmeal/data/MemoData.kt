package com.realmealboss.realmeal.data

import java.util.*

class MemoData (
    var id: String = UUID.randomUUID().toString(), // 고유 ID
    var createdAt: Date = Date(),   // 작성시간
    var title: String = "",         // 제목
    var content: String = "",       // 내용
    var summary: String = "",       // 내용요약
    var imageFile: String = "",     // 첨부이미지 파일이름
    var latitude: Double = 0.0,     // 위도
    var longitude: Double = 0.0,    // 경도
    var alarmTime: Date = Date(),   // 알람시간
    var weather: String = ""        // 날씨
)