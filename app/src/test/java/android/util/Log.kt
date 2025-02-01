package android.util

// Class created to handle logging during testing
object Log {
    @JvmStatic
    fun d(tag: String, msg: String): Int {
        println("DEBUG: $tag: $msg")
        return 0
    }

    @JvmStatic
    fun i(tag: String, msg: String): Int {
        println("INFO: $tag: $msg")
        return 0
    }

    @JvmStatic
    fun w(tag: String, msg: String): Int {
        println("WARN: $tag: $msg")
        return 0
    }

    @JvmStatic
    fun e(tag: String, msg: String): Int {
        println("ERROR: $tag: $msg")
        return 0
    }

    @JvmStatic
    fun v(tag: String, msg: String): Int {
        println("VERBOSE: $tag: $msg")
        return 0
    }

    @JvmStatic
    fun isLoggable(tag: String, level: Int): Boolean {
        return true
    }
}