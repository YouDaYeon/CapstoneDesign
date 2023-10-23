package com.example.myapplication

import java.util.UUID

data class Channel(
    val id: String = UUID.randomUUID().toString(),
    var title: String = "", // 채널 제목
    var description: String = "", // 채널 설명
    var imageUri: String? = null, // 이미지 URI (있을 경우)
    val myBusinessCardUri: String? = null, // 명함 URI
    val type: ChannelType = ChannelType.PROJECT, // 채널 유형
    var lecture: String? = null, // 강의 이름 (있을 경우)
    var className: String? = null, // 분반 (있을 경우)
    var role: String? = null, // 역할 (있을 경우)
    var competitionName: String? = null // 공모전 이름 (있을 경우)
) {
    // Firebase Realtime Database에 저장할 때 필요한 Map 반환
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "description" to description,
            "imageUri" to imageUri,
            "myBusinessCardUri" to myBusinessCardUri,
            "type" to type.name,
            "lecture" to lecture,
            "className" to className,
            "role" to role,
            "competitionName" to competitionName
        )
    }
}



