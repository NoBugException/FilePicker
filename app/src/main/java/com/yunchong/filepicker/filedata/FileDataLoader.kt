package com.yunchong.filepicker.filedata

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.yunchong.filepicker.filedata.FileDataManager.addFileListByType

/**
 * 文件数据参考
 */
object FileDataLoader {

    /**
     * MIME 列表
     * key：后缀  value：MIME类型
     *
     * 仅做参考，数据量较大，不会加载到缓存中
     */
//    val MIMETable : HashMap<String, String>  = hashMapOf(
//        "3gp" to "video/3gpp",
//        "3gpp" to "video/3gpp",
//        "aac" to "audio/x-mpeg",
//        "amr" to "audio/x-mpeg",
//        "apk" to "application/vndandroidpackage-archive",
//        "avi" to "video/x-msvideo",
//        "aab" to "application/x-authoware-bin",
//        "aam" to "application/x-authoware-map",
//        "aas" to "application/x-authoware-seg",
//        "ai" to "application/postscript",
//        "aif" to "audio/x-aiff",
//        "aifc" to "audio/x-aiff",
//        "aiff" to "audio/x-aiff",
//        "als" to "audio/x-alpha5",
//        "amc" to "application/x-mpeg",
//        "ani" to "application/octet-stream",
//        "asc" to "text/plain",
//        "asd" to "application/astound",
//        "asf" to "video/x-ms-asf",
//        "asn" to "application/astound",
//        "asp" to "application/x-asap",
//        "asx" to " video/x-ms-asf",
//        "au" to "audio/basic",
//        "avb" to "application/octet-stream",
//        "awb" to "audio/amr-wb",
//        "bcpio" to "application/x-bcpio",
//        "bld" to "application/bld",
//        "bld2" to "application/bld2",
//        "bpk" to "application/octet-stream",
//        "bz2" to "application/x-bzip2",
//        "bin" to "application/octet-stream",
//        "bmp" to "image/bmp",
//        "c" to "text/plain",
//        "class" to "application/octet-stream",
//        "conf" to "text/plain",
//        "cpp" to "text/plain",
//        "cal" to "image/x-cals",
//        "ccn" to "application/x-cnc",
//        "cco" to "application/x-cocoa",
//        "cdf" to "application/x-netcdf",
//        "cgi" to "magnus-internal/cgi",
//        "chat" to "application/x-chat",
//        "clp" to "application/x-msclip",
//        "cmx" to "application/x-cmx",
//        "co" to "application/x-cult3d-object",
//        "cod" to "image/cis-cod",
//        "cpio" to "application/x-cpio",
//        "cpt" to "application/mac-compactpro",
//        "crd" to "application/x-mscardfile",
//        "csh" to "application/x-csh",
//        "csm" to "chemical/x-csml",
//        "csml" to "chemical/x-csml",
//        "css" to "text/css",
//        "cur" to "application/octet-stream",
//        "doc" to "application/msword",
//        "dcm" to "x-lml/x-evm",
//        "dcr" to "application/x-director",
//        "dcx" to "image/x-dcx",
//        "dhtml" to "text/html",
//        "dir" to "application/x-director",
//        "dll" to "application/octet-stream",
//        "dmg" to "application/octet-stream",
//        "dms" to "application/octet-stream",
//        "dot" to "application/x-dot",
//        "dvi" to "application/x-dvi",
//        "dwf" to "drawing/x-dwf",
//        "dwg" to "application/x-autocad",
//        "dxf" to "application/x-autocad",
//        "dxr" to "application/x-director",
//        "ebk" to "application/x-expandedbook",
//        "emb" to "chemical/x-embl-dl-nucleotide",
//        "embl" to "chemical/x-embl-dl-nucleotide",
//        "eps" to "application/postscript",
//        "epub" to "application/epub+zip",
//        "eri" to "image/x-eri",
//        "es" to "audio/echospeech",
//        "esl" to "audio/echospeech",
//        "etc" to "application/x-earthtime",
//        "etx" to "text/x-setext",
//        "evm" to "x-lml/x-evm",
//        "evy" to "application/x-envoy",
//        "exe" to "application/octet-stream",
//        "fh4" to "image/x-freehand",
//        "fh5" to "image/x-freehand",
//        "fhc" to "image/x-freehand",
//        "fif" to "image/fif",
//        "fm" to "application/x-maker",
//        "fpx" to "image/x-fpx",
//        "fvi" to "video/isivideo",
//        "flv" to "video/x-msvideo",
//        "gau" to "chemical/x-gaussian-input",
//        "gca" to "application/x-gca-compressed",
//        "gdb" to "x-lml/x-gdb",
//        "gif" to "image/gif",
//        "gps" to "application/x-gps",
//        "gtar" to "application/x-gtar",
//        "gz" to "application/x-gzip",
//        "gif" to "image/gif",
//        "gtar" to "application/x-gtar",
//        "gz" to "application/x-gzip",
//        "h" to "text/plain",
//        "hdf" to "application/x-hdf",
//        "hdm" to "text/x-hdml",
//        "hdml" to "text/x-hdml",
//        "htm" to "text/html",
//        "html" to "text/html",
//        "hlp" to "application/winhlp",
//        "hqx" to "application/mac-binhex40",
//        "hts" to "text/html",
//        "ice" to "x-conference/x-cooltalk",
//        "ico" to "application/octet-stream",
//        "ief" to "image/ief",
//        "ifm" to "image/gif",
//        "ifs" to "image/ifs",
//        "imy" to "audio/melody",
//        "ins" to "application/x-net-install",
//        "ips" to "application/x-ipscript",
//        "ipx" to "application/x-ipix",
//        "it" to "audio/x-mod",
//        "itz" to "audio/x-mod",
//        "ivr" to "i-world/i-vrml",
//        "j2k" to "image/j2k",
//        "jad" to "text/vndsunj2meapp-descriptor",
//        "jam" to "application/x-jam",
//        "jnlp" to "application/x-java-jnlp-file",
//        "jpe" to "image/jpeg",
//        "jpz" to "image/jpeg",
//        "jwc" to "application/jwc",
//        "jar" to "application/java-archive",
//        "java" to "text/plain",
//        "jpeg" to "image/jpeg",
//        "jpg" to "image/jpeg",
//        "js" to "application/x-javascript",
//        "kjx" to "application/x-kjx",
//        "lak" to "x-lml/x-lak",
//        "latex" to "application/x-latex",
//        "lcc" to "application/fastman",
//        "lcl" to "application/x-digitalloca",
//        "lcr" to "application/x-digitalloca",
//        "lgh" to "application/lgh",
//        "lha" to "application/octet-stream",
//        "lml" to "x-lml/x-lml",
//        "lmlpack" to "x-lml/x-lmlpack",
//        "log" to "text/plain",
//        "lsf" to "video/x-ms-asf",
//        "lsx" to "video/x-ms-asf",
//        "lzh" to "application/x-lzh ",
//        "m13" to "application/x-msmediaview",
//        "m14" to "application/x-msmediaview",
//        "m15" to "audio/x-mod",
//        "m3u" to "audio/x-mpegurl",
//        "m3url" to "audio/x-mpegurl",
//        "ma1" to "audio/ma1",
//        "ma2" to "audio/ma2",
//        "ma3" to "audio/ma3",
//        "ma5" to "audio/ma5",
//        "man" to "application/x-troff-man",
//        "map" to "magnus-internal/imagemap",
//        "mbd" to "application/mbedlet",
//        "mct" to "application/x-mascot",
//        "mdb" to "application/x-msaccess",
//        "mdz" to "audio/x-mod",
//        "me" to "application/x-troff-me",
//        "mel" to "text/x-vmel",
//        "mi" to "application/x-mif",
//        "mid" to "audio/midi",
//        "midi" to "audio/midi",
//        "m4a" to "audio/mp4a-latm",
//        "m4b" to "audio/mp4a-latm",
//        "m4p" to "audio/mp4a-latm",
//        "m4u" to "video/vndmpegurl",
//        "m4v" to "video/x-m4v",
//        "mov" to "video/quicktime",
//        "mp2" to "audio/x-mpeg",
//        "mp3" to "audio/x-mpeg",
//        "mp4" to "video/mp4",
//        "mpc" to "application/vndmpohuncertificate",
//        "mpe" to "video/mpeg",
//        "mpeg" to "video/mpeg",
//        "mpg" to "video/mpeg",
//        "mpg4" to "video/mp4",
//        "mpga" to "audio/mpeg",
//        "msg" to "application/vndms-outlook",
//        "mif" to "application/x-mif",
//        "mil" to "image/x-cals",
//        "mio" to "audio/x-mio",
//        "mmf" to "application/x-skt-lbs",
//        "mng" to "video/x-mng",
//        "mny" to "application/x-msmoney",
//        "moc" to "application/x-mocha",
//        "mocha" to "application/x-mocha",
//        "mod" to "audio/x-mod",
//        "mof" to "application/x-yumekara",
//        "mol" to "chemical/x-mdl-molfile",
//        "mop" to "chemical/x-mopac-input",
//        "movie" to "video/x-sgi-movie",
//        "mpn" to "application/vndmophunapplication",
//        "mpp" to "application/vndms-project",
//        "mps" to "application/x-mapserver",
//        "mrl" to "text/x-mrml",
//        "mrm" to "application/x-mrm",
//        "ms" to "application/x-troff-ms",
//        "mts" to "application/metastream",
//        "mtx" to "application/metastream",
//        "mtz" to "application/metastream",
//        "mzv" to "application/metastream",
//        "nar" to "application/zip",
//        "nbmp" to "image/nbmp",
//        "nc" to "application/x-netcdf",
//        "ndb" to "x-lml/x-ndb",
//        "ndwn" to "application/ndwn",
//        "nif" to "application/x-nif",
//        "nmz" to "application/x-scream",
//        "nokia-op-logo" to "image/vndnok-oplogo-color",
//        "npx" to "application/x-netfpx",
//        "nsnd" to "audio/nsnd",
//        "nva" to "application/x-neva1",
//        "oda" to "application/oda",
//        "oom" to "application/x-atlasMate-plugin",
//        "ogg" to "audio/ogg",
//        "pac" to "audio/x-pac",
//        "pae" to "audio/x-epac",
//        "pan" to "application/x-pan",
//        "pbm" to "image/x-portable-bitmap",
//        "pcx" to "image/x-pcx",
//        "pda" to "image/x-pda",
//        "pdb" to "chemical/x-pdb",
//        "pdf" to "application/pdf",
//        "pfr" to "application/font-tdpfr",
//        "pgm" to "image/x-portable-graymap",
//        "pict" to "image/x-pict",
//        "pm" to "application/x-perl",
//        "pmd" to "application/x-pmd",
//        "png" to "image/png",
//        "pnm" to "image/x-portable-anymap",
//        "pnz" to "image/png",
//        "pot" to "application/vndms-powerpoint",
//        "ppm" to "image/x-portable-pixmap",
//        "pps" to "application/vndms-powerpoint",
//        "ppt" to "application/vndms-powerpoint",
//        "pqf" to "application/x-cprplayer",
//        "pqi" to "application/cprplayer",
//        "prc" to "application/x-prc",
//        "proxy" to "application/x-ns-proxy-autoconfig",
//        "prop" to "text/plain",
//        "ps" to "application/postscript",
//        "ptlk" to "application/listenup",
//        "pub" to "application/x-mspublisher",
//        "pvx" to "video/x-pv-pvx",
//        "qcp" to "audio/vndqcelp",
//        "qt" to "video/quicktime",
//        "qti" to "image/x-quicktime",
//        "qtif" to "image/x-quicktime",
//        "r3t" to "text/vndrn-realtext3d",
//        "ra" to "audio/x-pn-realaudio",
//        "ram" to "audio/x-pn-realaudio",
//        "ras" to "image/x-cmu-raster",
//        "rdf" to "application/rdf+xml",
//        "rf" to "image/vndrn-realflash",
//        "rgb" to "image/x-rgb",
//        "rlf" to "application/x-richlink",
//        "rm" to "audio/x-pn-realaudio",
//        "rmf" to "audio/x-rmf",
//        "rmm" to "audio/x-pn-realaudio",
//        "rnx" to "application/vndrn-realplayer",
//        "roff" to "application/x-troff",
//        "rp" to "image/vndrn-realpix",
//        "rpm" to "audio/x-pn-realaudio-plugin",
//        "rt" to "text/vndrn-realtext",
//        "rte" to "x-lml/x-gps",
//        "rtf" to "application/rtf",
//        "rtg" to "application/metastream",
//        "rtx" to "text/richtext",
//        "rv" to "video/vndrn-realvideo",
//        "rwc" to "application/x-rogerwilco",
//        "rar" to "application/rar",
//        "rc" to "text/plain",
//        "rmvb" to "audio/x-pn-realaudio",
//        "s3m" to "audio/x-mod",
//        "s3z" to "audio/x-mod",
//        "sca" to "application/x-supercard",
//        "scd" to "application/x-msschedule",
//        "sdf" to "application/e-score",
//        "sea" to "application/x-stuffit",
//        "sgm" to "text/x-sgml",
//        "sgml" to "text/x-sgml",
//        "shar" to "application/x-shar",
//        "shtml" to "magnus-internal/parsed-html",
//        "shw" to "application/presentations",
//        "si6" to "image/si6",
//        "si7" to "image/vndstiwapsis",
//        "si9" to "image/vndlgtwapsis",
//        "sis" to "application/vndsymbianinstall",
//        "sit" to "application/x-stuffit",
//        "skd" to "application/x-koan",
//        "skm" to "application/x-koan",
//        "skp" to "application/x-koan",
//        "skt" to "application/x-koan",
//        "slc" to "application/x-salsa",
//        "smd" to "audio/x-smd",
//        "smi" to "application/smil",
//        "smil" to "application/smil",
//        "smp" to "application/studiom",
//        "smz" to "audio/x-smd",
//        "sh" to "application/x-sh",
//        "snd" to "audio/basic",
//        "spc" to "text/x-speech",
//        "spl" to "application/futuresplash",
//        "spr" to "application/x-sprite",
//        "sprite" to "application/x-sprite",
//        "sdp" to "application/sdp",
//        "spt" to "application/x-spt",
//        "src" to "application/x-wais-source",
//        "stk" to "application/hyperstudio",
//        "stm" to "audio/x-mod",
//        "sv4cpio" to "application/x-sv4cpio",
//        "sv4crc" to "application/x-sv4crc",
//        "svf" to "image/vnd",
//        "svg" to "image/svg-xml",
//        "svh" to "image/svh",
//        "svr" to "x-world/x-svr",
//        "swf" to "application/x-shockwave-flash",
//        "swfl" to "application/x-shockwave-flash",
//        "t" to "application/x-troff",
//        "tad" to "application/octet-stream",
//        "talk" to "text/x-speech",
//        "tar" to "application/x-tar",
//        "taz" to "application/x-tar",
//        "tbp" to "application/x-timbuktu",
//        "tbt" to "application/x-timbuktu",
//        "tcl" to "application/x-tcl",
//        "tex" to "application/x-tex",
//        "texi" to "application/x-texinfo",
//        "texinfo" to "application/x-texinfo",
//        "tgz" to "application/x-tar",
//        "thm" to "application/vnderithm",
//        "tif" to "image/tiff",
//        "tiff" to "image/tiff",
//        "tki" to "application/x-tkined",
//        "tkined" to "application/x-tkined",
//        "toc" to "application/toc",
//        "toy" to "image/toy",
//        "tr" to "application/x-troff",
//        "trk" to "x-lml/x-gps",
//        "trm" to "application/x-msterminal",
//        "tsi" to "audio/tsplayer",
//        "tsp" to "application/dsptype",
//        "tsv" to "text/tab-separated-values",
//        "ttf" to "application/octet-stream",
//        "ttz" to "application/t-time",
//        "txt" to "text/plain",
//        "ult" to "audio/x-mod",
//        "ustar" to "application/x-ustar",
//        "uu" to "application/x-uuencode",
//        "uue" to "application/x-uuencode",
//        "vcd" to "application/x-cdlink",
//        "vcf" to "text/x-vcard",
//        "vdo" to "video/vdo",
//        "vib" to "audio/vib",
//        "viv" to "video/vivo",
//        "vivo" to "video/vivo",
//        "vmd" to "application/vocaltec-media-desc",
//        "vmf" to "application/vocaltec-media-file",
//        "vmi" to "application/x-dreamcast-vms-info",
//        "vms" to "application/x-dreamcast-vms",
//        "vox" to "audio/voxware",
//        "vqe" to "audio/x-twinvq-plugin",
//        "vqf" to "audio/x-twinvq",
//        "vql" to "audio/x-twinvq",
//        "vre" to "x-world/x-vream",
//        "vrml" to "x-world/x-vrml",
//        "vrt" to "x-world/x-vrt",
//        "vrw" to "x-world/x-vream",
//        "vts" to "workbook/formulaone",
//        "wax" to "audio/x-ms-wax",
//        "wbmp" to "image/vndwapwbmp",
//        "web" to "application/vndxara",
//        "wav" to "audio/x-wav",
//        "wma" to "audio/x-ms-wma",
//        "wmv" to "audio/x-ms-wmv",
//        "wi" to "image/wavelet",
//        "wis" to "application/x-InstallShield",
//        "wm" to "video/x-ms-wm",
//        "wmd" to "application/x-ms-wmd",
//        "wmf" to "application/x-msmetafile",
//        "wml" to "text/vndwapwml",
//        "wmlc" to "application/vndwapwmlc",
//        "wmls" to "text/vndwapwmlscript",
//        "wmlsc" to "application/vndwapwmlscriptc",
//        "wmlscript" to "text/vndwapwmlscript",
//        "wmv" to "video/x-ms-wmv",
//        "wmx" to "video/x-ms-wmx",
//        "wmz" to "application/x-ms-wmz",
//        "wpng" to "image/x-up-wpng",
//        "wps" to "application/vndms-works",
//        "wpt" to "x-lml/x-gps",
//        "wri" to "application/x-mswrite",
//        "wrl" to "x-world/x-vrml",
//        "wrz" to "x-world/x-vrml",
//        "ws" to "text/vndwapwmlscript",
//        "wsc" to "application/vndwapwmlscriptc",
//        "wv" to "video/wavelet",
//        "wvx" to "video/x-ms-wvx",
//        "wxl" to "application/x-wxl",
//        "x-gzip" to "application/x-gzip",
//        "xar" to "application/vndxara",
//        "xbm" to "image/x-xbitmap",
//        "xdm" to "application/x-xdma",
//        "xdma" to "application/x-xdma",
//        "xdw" to "application/vndfujixeroxdocuworks",
//        "xht" to "application/xhtml+xml",
//        "xhtm" to "application/xhtml+xml",
//        "xhtml" to "application/xhtml+xml",
//        "xla" to "application/vndms-excel",
//        "xlc" to "application/vndms-excel",
//        "xll" to "application/x-excel",
//        "xlm" to "application/vndms-excel",
//        "xls" to "application/vndms-excel",
//        "xlt" to "application/vndms-excel",
//        "xlw" to "application/vndms-excel",
//        "xm" to "audio/x-mod",
//        "xml" to "text/xml",
//        "xmz" to "audio/x-mod",
//        "xpi" to "application/x-xpinstall",
//        "xpm" to "image/x-xpixmap",
//        "xsit" to "text/xml",
//        "xsl" to "text/xml",
//        "xul" to "text/xul",
//        "xwd" to "image/x-xwindowdump",
//        "xyz" to "chemical/x-pdb",
//        "yz1" to "application/x-yz1",
//        "z" to "application/x-compress",
//        "zac" to "application/x-zaurus-zac",
//        "zip" to "application/zip",
//        "" to "*/*")

    /**
     * 加载文件数据
     */
    fun loadFileData(mContentResolver: ContentResolver) {
        addFileListByType(FileSystemType.PHOTO, getAllPhoto(mContentResolver))
        addFileListByType(FileSystemType.MUSIC, getAllMusic(mContentResolver))
        addFileListByType(FileSystemType.VIDEO, getAllVideo(mContentResolver))
        addFileListByType(FileSystemType.TEXT, getAllText(mContentResolver))
        addFileListByType(FileSystemType.ZIP, getAllZip(mContentResolver))
        addFileListByType(FileSystemType.BIN, getAllBin(mContentResolver))
    }

    /**
     * 获取所有图片
     */
    private fun getAllPhoto(mContentResolver: ContentResolver?): List<FileItem> {
        val photos: MutableList<FileItem> = ArrayList()
        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DISPLAY_NAME
        )
        var cursor: Cursor? = null
        try {
            // 第一个参数 uri：指定输出文件类型
            // 第二个参数 projection：指定输出字段
            // 第三个参数 selection 通常的 sql 语句，例如 selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? "
            // 第四个参数 selectionArgs = new String[]{"jpg"};
            // 第三个参数 和 第四个参数 决定输出条件
            // 第五个参数 排序 asc 按升序排列 desc 按降序排列
            cursor = mContentResolver?.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_MODIFIED + " desc"
            )
            cursor?.let {
                while (it.moveToNext()) {
                    val idColumnIndex: Int = it.getColumnIndex(MediaStore.Images.ImageColumns._ID)
                    val nameColumnIndex: Int = it.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)
                    val dataColumnIndex: Int = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    var imageId: String = it.getString(idColumnIndex)
                    var fileName: String = it.getString(nameColumnIndex)
                    var filePath: String = it.getString(dataColumnIndex)
                    val fileItem = FileItem(FileSystemType.PHOTO.ordinal, imageId, filePath, fileName)
                    photos.add(fileItem)
                }
            }
        } catch (e: Exception) {
            // 输出错误日志
        } finally {
            cursor?.close()
        }
        return photos
    }

    /**
     * 获取所有音频文件
     */
    private fun getAllMusic(mContentResolver: ContentResolver?): List<FileItem> {
        val musics: MutableList<FileItem> = ArrayList()
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.DISPLAY_NAME
        )
        var cursor: Cursor? = null
        try {
            // 第一个参数 uri：指定输出文件类型
            // 第二个参数 projection：指定输出字段
            // 第三个参数 selection 通常的 sql 语句，例如 selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? "
            // 第四个参数 selectionArgs = new String[]{"jpg"};
            // 第三个参数 和 第四个参数 决定输出条件
            // 第五个参数 排序 asc 按升序排列 desc 按降序排列
            cursor = mContentResolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Audio.AudioColumns.DATE_MODIFIED + " desc"
            )
            cursor?.let {
                while (it.moveToNext()) {
                    val idColumnIndex: Int = it.getColumnIndex(MediaStore.Audio.AudioColumns._ID)
                    val nameColumnIndex: Int = it.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
                    val dataColumnIndex: Int = it.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)
                    var fileId: String = it.getString(idColumnIndex)
                    var fileName: String = it.getString(nameColumnIndex)
                    var filePath: String = it.getString(dataColumnIndex)
                    val fileItem = FileItem(FileSystemType.MUSIC.ordinal, fileId, filePath, fileName)
                    musics.add(fileItem)
                }
            }
        } catch (e: Exception) {
            // 输出错误日志
        } finally {
            cursor?.close()
        }
        return musics
    }

    /**
     * 获取所有视频文件
     */
    private fun getAllVideo(mContentResolver: ContentResolver?): List<FileItem> {
        val videos: MutableList<FileItem> = ArrayList()
        val projection = arrayOf(
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.VideoColumns.DISPLAY_NAME
        )
        var cursor: Cursor? = null
        try {
            // 第一个参数 uri：指定输出文件类型
            // 第二个参数 projection：指定输出字段
            // 第三个参数 selection 通常的 sql 语句，例如 selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? "
            // 第四个参数 selectionArgs = new String[]{"jpg"};
            // 第三个参数 和 第四个参数 决定输出条件
            // 第五个参数 排序 asc 按升序排列 desc 按降序排列
            cursor = mContentResolver?.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Video.VideoColumns.DATE_MODIFIED + " desc"
            )
            cursor?.let {
                while (it.moveToNext()) {
                    val idColumnIndex: Int = it.getColumnIndex(MediaStore.Video.VideoColumns._ID)
                    val nameColumnIndex: Int = it.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
                    val dataColumnIndex: Int = it.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
                    var fileId: String = it.getString(idColumnIndex)
                    var fileName: String = it.getString(nameColumnIndex)
                    var filePath: String = it.getString(dataColumnIndex)
                    val fileItem = FileItem(FileSystemType.VIDEO.ordinal, fileId, filePath, fileName)
                    videos.add(fileItem)
                }
            }
        } catch (e: Exception) {
            // 输出错误日志
        } finally {
            cursor?.close()
        }
        return videos
    }

    /**
     * 获取所有的文本文件
     */
    private fun getAllText(mContentResolver: ContentResolver?): List<FileItem> {
        val texts: MutableList<FileItem> = ArrayList()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.DISPLAY_NAME
        )
        val selection = (MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ")
        val selectionArgs = arrayOf(
            "text/plain",
            "application/msword",
            "application/pdf",
            "application/vnd.ms-powerpoint",
            "application/vnd.ms-excel"
        )
        var cursor: Cursor? = null
        try {
            // 第一个参数 uri：指定输出文件类型
            // 第二个参数 projection：指定输出字段
            // 第三个参数 selection 通常的 sql 语句，例如 selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? "
            // 第四个参数 selectionArgs = new String[]{"jpg"};
            // 第三个参数 和 第四个参数 决定输出条件
            // 第五个参数 排序 asc 按升序排列 desc 按降序排列
            cursor = mContentResolver?.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " desc"
            )
            cursor?.let {
                while (it.moveToNext()) {
                    val idColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
                    val nameColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                    val dataColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                    var fileId: String = it.getString(idColumnIndex)
                    var fileName: String = it.getString(nameColumnIndex)
                    var filePath: String = it.getString(dataColumnIndex)
                    val fileItem = FileItem(FileSystemType.TEXT.ordinal, fileId, filePath, fileName)
                    texts.add(fileItem)
                }
            }
        } catch (e: Exception) {
            // 输出错误日志
        } finally {
            cursor?.close()
        }
        return texts
    }

    /**
     * 获取所有的zip格式的文件
     */
    private fun getAllZip(mContentResolver: ContentResolver?): List<FileItem> {
        val zips: MutableList<FileItem> = ArrayList()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DISPLAY_NAME
        )
        val selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
        val selectionArgs = arrayOf("application/zip")
        var cursor: Cursor? = null
        try {
            cursor = mContentResolver?.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " desc"
            )
            cursor?.let {
                while (it.moveToNext()) {
                    val idColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
                    val nameColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                    val dataColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                    var fileId: String = it.getString(idColumnIndex)
                    var fileName: String = it.getString(nameColumnIndex)
                    var filePath: String = it.getString(dataColumnIndex)
                    val fileItem = FileItem(FileSystemType.ZIP.ordinal, fileId, filePath, fileName)
                    zips.add(fileItem)
                }
            }
        } catch (e: Exception) {
            // 输出错误日志
        } finally {
            cursor?.close()
        }
        return zips
    }

    /**
     * 获取所有的bin格式的文件
     */
    private fun getAllBin(mContentResolver: ContentResolver?): List<FileItem> {
        val zips: MutableList<FileItem> = ArrayList()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DISPLAY_NAME

        )

        val selection = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.bin'" + ")"
        var cursor: Cursor? = null
        try {
            cursor = mContentResolver?.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " desc"
            )
            cursor?.let {
                while (it.moveToNext()) {
                    val idColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
                    val nameColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                    val dataColumnIndex: Int = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                    var fileId: String = it.getString(idColumnIndex)
                    var fileName: String = it.getString(nameColumnIndex)
                    var filePath: String = it.getString(dataColumnIndex)
                    val fileItem = FileItem(FileSystemType.BIN.ordinal, fileId, filePath, fileName)
                    zips.add(fileItem)
                }
            }
        } catch (e: Exception) {
            // 输出错误日志
        } finally {
            cursor?.close()
        }
        return zips
    }
}