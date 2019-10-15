package com.meditrust.module_base.manager

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*

/**
 * @author：created by zhongchao.wang
 * @date：2019/7/23
 * @desc: activity的管理类
 */
class ActivityManager
/**
 * 隐藏构造器
 *
 * @param isBridge 是否开启桥接模式
 */
private constructor(isBridge: Boolean) {

    /** 是否使用桥接模式  */
    private var isBridge = false
    /** AppManager管理activity的委托类  */
    private val mDelegate: ActivityManagerDelegate
    /** 维护activity的栈结构  */
    private lateinit var mActivityStack: Stack<Activity>

    init {
        this.isBridge = isBridge
        mDelegate = ActivityManagerDelegate.instance!!
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity activity实例
     */
    fun addActivity(activity: Activity) {
        if (isBridge) {
            mDelegate.addActivity(activity)
        } else {
            if (mActivityStack == null) {
                mActivityStack = Stack()
            }
            mActivityStack!!.add(activity)
        }
    }

    /**
     * 移除栈
     * @param activity
     */
    fun removeActivity(activity: Activity) {
        if (isBridge) {
            mDelegate.removeActivity(activity)
        } else {
            if (mActivityStack == null) {
                mActivityStack = Stack()
            }
            mActivityStack!!.remove(activity)
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     * @return 当前（栈顶）activity
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun currentActivity(): Activity? {
        return if (isBridge) {
            mDelegate.currentActivity()
        } else {
            if (mActivityStack != null && !mActivityStack!!.isEmpty()) {
                if (!mActivityStack!!.lastElement().isDestroyed) {
                    mActivityStack!!.lastElement()
                } else {
                    topActivity()
                }
            } else null
        }
    }

    /**
     * 获取栈底Activity
     * @return 当前（栈底）activity
     */
    fun topActivity(): Activity? {
        return if (isBridge) {
            mDelegate.topActivity()
        } else {
            if (mActivityStack != null && !mActivityStack!!.isEmpty()) {
                mActivityStack!!.firstElement()
            } else null
        }
    }

    /**
     * 结束除当前activtiy以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     *
     * @param activity 不需要结束的activity
     */
    fun finishOtherActivity(activity: Activity) {
        if (isBridge) {
            mDelegate.finishOtherActivity(activity)
        } else {
            if (mActivityStack != null) {
                val it = mActivityStack!!.iterator()
                while (it.hasNext()) {
                    val temp = it.next()
                    if (temp != null && temp !== activity) {
                        finishActivity(temp)
                    }
                }
            }
        }
    }

    /**
     * 结束除这一类activtiy以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     * @param cls 不需要结束的activity
     */
    fun finishOtherActivity(cls: Class<*>) {
        if (isBridge) {
            mDelegate.finishOtherActivity(cls)
        } else {
            if (mActivityStack != null) {
                val it = mActivityStack!!.iterator()
                while (it.hasNext()) {
                    val activity = it.next()
                    if (activity.javaClass != cls) {
                        finishActivity(activity)
                    }
                }
            }
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        if (isBridge) {
            mDelegate.finishActivity()
        } else {
            if (mActivityStack != null && !mActivityStack!!.isEmpty()) {
                val activity = mActivityStack!!.lastElement()
                finishActivity(activity)
            }
        }
    }

    /**
     * 结束指定的Activity
     * @param activity 指定的activity实例
     */
    fun finishActivity(activity: Activity?) {
        if (isBridge) {
            mDelegate.finishActivity(activity)
        } else {
            if (activity != null) {
                // 兼容未使用AppManager管理的实例
                if (mActivityStack != null && mActivityStack!!.contains(activity)) {
                    mActivityStack!!.remove(activity)
                }
                activity.finish()
            }
        }
    }

    /**
     * 结束指定类名的所有Activity
     * @param cls 指定的类的class
     */
    fun finishActivity(cls: Class<*>) {
        if (isBridge) {
            mDelegate.finishActivity(cls)
        } else {
            if (mActivityStack != null) {
                val it = mActivityStack!!.iterator()
                while (it.hasNext()) {
                    val activity = it.next()
                    if (activity.javaClass == cls) {
                        finishActivity(activity)
                    }
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        if (isBridge) {
            mDelegate.finishAllActivity()
        } else {
            if (mActivityStack != null) {
                var i = 0
                val size = mActivityStack!!.size
                while (i < size) {
                    if (null != mActivityStack!![i]) {
                        mActivityStack!![i].finish()
                    }
                    i++
                }
                mActivityStack!!.clear()
            }
        }
    }

    /**
     * 退出应用程序
     */
    fun exitApp() {
        if (isBridge) {
            mDelegate.exitApp()
        } else {
            try {
                finishAllActivity()
                // 退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
                System.exit(0)
                // 从操作系统中结束掉当前程序的进程
                android.os.Process.killProcess(android.os.Process.myPid())
            } catch (e: Exception) {
                Log.e("Exit exception", e.message)
            }

        }
    }

    companion object {
        @Volatile
        private var sInstance: ActivityManager? = null
        /**
         * 单例
         * @return 返回ActivityManager的单例
         */
        val instance: ActivityManager?
            get() {
                if (sInstance == null) {
                    sInstance = ActivityManager(true)
                }
                return sInstance
            }
    }

}
