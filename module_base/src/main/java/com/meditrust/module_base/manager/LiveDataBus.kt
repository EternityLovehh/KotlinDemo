package com.meditrust.module_base.manager

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.meditrust.module_base.manager.LiveDataBus.ObserverWrapper

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/11
 * @desc: livedatabus 事件总线
 */
class LiveDataBus {
    var bus: HashMap<String, BusMutableLiveData<Any>> = hashMapOf()

    companion object {
        @Volatile
        private var sInstance: LiveDataBus? = null
        /**
         * 单例
         * @return 返回ActivityManager的单例
         */
        var instance: LiveDataBus? = null
            get() {
                instance = LiveDataBus()
                return sInstance
            }
    }

    fun <T> with(key: String, type: Class<T>): MutableLiveData<Any> {
        if (!bus.containsKey(key)) {
            bus[key] = BusMutableLiveData()
        }
        return bus[key]!!
    }

    fun with(key: String): MutableLiveData<Any> {
        return with(key, Any::class.java)
    }


    class BusMutableLiveData<T> : MutableLiveData<T>() {
        val observerMap: HashMap<Observer<in T>, Observer<in T>> = hashMapOf()
        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            try {
                hook(observer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun observeForever(observer: Observer<in T>) {
            if (!observerMap.containsKey(observer)) {
                observerMap[observer] = ObserverWrapper(observer)
            }
            observerMap[observer]?.let { super.observeForever(it) }
        }

        override fun removeObserver(observer: Observer<in T>) {
            val realObserver: Observer<in T>? = if (observerMap.containsKey(observer)) {
                observerMap.remove(observer) as Observer<T>?
            } else {
                observer
            }
            super.removeObserver(realObserver!!)
        }

        private fun hook(observer: Observer<in T>) {
            val classLiveData = LiveData::class.java
            val fieldObservers = classLiveData.getDeclaredField("mObservers")
            fieldObservers.isAccessible = true
            val objectObservers = fieldObservers.get(this)
            val classObservers = objectObservers!!.javaClass
            val methodGet = classObservers.getDeclaredMethod("get", Any::class.java)
            methodGet.isAccessible = true
            val objectWrapperEntry = methodGet.invoke(objectObservers, observer)
            var objectWrapper: Any? = null
            if (objectWrapperEntry is java.util.Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw NullPointerException("Wrapper can not be bull!")
            }
            val classObserverWrapper = objectWrapper.javaClass.superclass
            val fieldLastVersion = classObserverWrapper?.getDeclaredField("mLastVersion")
            fieldLastVersion?.isAccessible = true
            //get livedata's version
            val fieldVersion = classLiveData.getDeclaredField("mVersion")
            fieldVersion.isAccessible = true
            val objectVersion = fieldVersion.get(this)
            //set wrapper's version
            fieldLastVersion?.set(objectWrapper, objectVersion)
        }
    }

    private class ObserverWrapper<T>(private val observer: Observer<T>?) : Observer<T> {

        private val isCallOnObserve: Boolean
            get() {
                val stackTrace = Thread.currentThread().stackTrace
                if (stackTrace != null && stackTrace.isNotEmpty()) {
                    for (element in stackTrace) {
                        if ("android.arch.lifecycle.LiveData" == element.className && "observeForever" == element.methodName) {
                            return true
                        }
                    }
                }
                return false
            }

        override fun onChanged(t: T) {
            if (observer != null) {
                if (isCallOnObserve) {
                    return
                }
                observer.onChanged(t)
            }
        }
    }
}