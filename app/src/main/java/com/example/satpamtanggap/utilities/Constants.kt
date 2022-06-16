package com.example.satpamtanggap.utilities

class Constants {
    companion object{
        const val KEY_COLLECTION_USERS = "warga"
        const val KEY_COLLECTION_SATPAM = "satpam"
        const val KEY_NAME = "nama"
        const val KEY_ADDRESS = "address"
        const val KEY_TELEPON = "telepon"
        const val KEY_EMAIL = "email"
        const val KEY_ROLE = "role"
        const val KEY_PASSWORD2 = "password2"
        const val KEY_PREFERENCE_NAME = "satpamAppPreference"
        const val KEY_IS_SIGNED_IN = "IsSignedIn"
        const val KEY_USER_ID = "usersId"
        const val KEY_USER_EMAIL = "usersEmail"
        const val KEY_USER_P = "usersp"
        const val KEY_IMAGE = "image"
        const val DEFAULT_IMAGE_PROFILE = "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"
        const val KEY_FCM_TOKEN = "fcmToken"
        const val KEY_ROLE_WARGA = "warga"
        const val KEY_TIMESTAMP = "timestamp"

        //LAPORAN
        const val KEY_LAPORAN_JDL = "judul"
        const val KEY_LAPORAN_ISI = "isi"
        const val KEY_COLLECTION_LAPORAN = "laporan"
        const val KEY_SENDER_NAME = "pengirim"

        //CHAT
        const val KEY_COLLECTION_CHAT = "chat"
        const val KEY_SENDER_ID = "senderId"
        const val KEY_RECEIVER_ID = "receiverId"
        const val KEY_MESSAGE = "message"

        //RECENT
        const val KEY_COLLECTION_CONVERSATIONS = "conversations"
        const val KEY_NAME_SENDER = "senderName"
        const val KEY_IMAGE_SENDER = "senderImage"
        const val KEY_NAME_RECEIVER = "receiverName"
        const val KEY_IMAGE_RECEIVER = "receiverImage"
        const val KEY_LAST_MESSAGE = "lastMessage"

    }
}