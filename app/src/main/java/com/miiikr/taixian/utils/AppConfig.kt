package com.ssh.net.ssh.utils

class AppConfig {

    companion object {
        val WB_KEY_APP = "4134372629"
        val REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"
        val SCOPE = ("email,direct_messages_read,direct_messages_write,"
                + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                + "follow_app_official_microblog,"
                + "invitation_write")

        val isDeBug = true
        val debugUrl = "http://192.168.101.101:8080/"
        val lineUrl = ""

        val MAIN_CHANGE_SEX_MAN = 1
        val MAIN_CHANGE_SEX_WOMEN = 2
        val FRAGMENT_RIGHT_OPEN_SET_ID = 9

        val FRAGMENT_RIGHT_OPEN_NICK_NAME = 8
        val FRAGMENT_RIGHT_OPEN_NICK_PHONE = 9
        val FRAGMENT_RIGHT_OPEN_CLOSE = 10
        val FRAGMENT_LEFT_OPEN_CLOSE = 11


        val REQUEST_ID_GET_PIC = 0


    }


}