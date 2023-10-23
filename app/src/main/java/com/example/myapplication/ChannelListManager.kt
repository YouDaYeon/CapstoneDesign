package com.example.myapplication

object ChannelListManager {
    private val channelList: MutableList<Channel> = mutableListOf()

    // ChannelListManager 초기화 함수
    fun initialize(channels: List<Channel>) {
        channelList.clear()
        channelList.addAll(channels)
    }
    fun getChannels(): List<Channel> {
        return channelList
    }

    fun addChannel(channel: Channel) {
        channelList.add(channel)
    }
    fun getChannelById(id: String?): Channel? {
        return channelList.find { it.id == id }
    }
}

