package com.yunchong.filepicker.filedata

enum class FileSystemType {
    PHOTO, MUSIC, VIDEO, TEXT, ZIP, BIN;

    companion object {
        @JvmStatic
        fun getFileTypeByOrdinal(ordinal: Int): FileSystemType {
            for (type in values()) {
                if (type.ordinal == ordinal) {
                    return type
                }
            }
            return PHOTO
        }
    }
}