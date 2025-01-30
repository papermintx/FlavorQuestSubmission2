package com.dicoding.flavorquest.utilities

import android.text.TextUtils
import android.util.Log
import com.dicoding.flavorquest.utilities.DynamicFeatureUtils.TAG
import java.lang.reflect.Method
import java.lang.reflect.Modifier


fun findMethodByReflection(classMethod: Class<*>?, methodName: String): Method? {
    return try {
        if (!TextUtils.isEmpty(methodName)) {
            classMethod?.let { clazz ->
                clazz.methods.find { it.name.equals(methodName) && Modifier.isStatic(it.modifiers) }
            } ?: run {
                null
            }
        } else {
            null
        }
    }catch (e:Throwable){
        Log.d(TAG, "findMethodByReflection: ${e.message}")
        null
    }
}

fun loadClassByReflection(className: String): Class<*>? {
    return try {
        val classLoader = ::loadClassByReflection.javaClass.classLoader
        classLoader?.loadClass(className)
    } catch (e: Throwable) {
        Log.d(TAG, "loadClassByReflection: ${e.message}")
        null
    }
}

fun invokeMethod(method: Method, obj: Any?, vararg args: Any): Boolean {
    return try {
        Log.d(TAG, "Invoking method: ${method.name} on $obj with arguments ${args.joinToString()}")
        method.invoke(obj, *args)
        true
    } catch (e: Throwable) {
        Log.e(TAG, "invokeMethod: Failed to invoke ${method.name} - ${e.message}", e)
        false
    }
}
