package com.meditrust.module_base.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import java.util.regex.Pattern

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/8
 * @desc:
 */
object ProvateUtil {
    /**
     * 移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、170、173、177、180、181、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    fun validateMobile(number: String): Boolean {
        //"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        val num = "[1][34578]\\d{9}"
        return if (TextUtils.isEmpty(number)) {
            false
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            number.matches(num.toRegex())
        }
    }

    /**
     * 验证邮政编码
     * @param postCode
     * @return
     */
    fun validatePostCode(postCode: String): Boolean {
        val reg = "[1-9]\\d{5}"
        return Pattern.matches(reg, postCode)
    }

    /**
     * 验证密码
     * @param context
     * @param str
     * @return
     */
    fun validatePwd(context: Context, str: String): Boolean {
        return if (str.matches(".{6,16}".toRegex()) && str.matches("\\S*".toRegex())
            && str.matches("(.*\\D.*){1,8}|.{9,}".toRegex())
        ) {
            true
        } else {
            if (!str.matches(".{6,16}".toRegex())) {
                Toast.makeText(context, "密码长度为6-16个字符", Toast.LENGTH_SHORT).show()
            } else if (!str.matches("\\S*".toRegex())) {
                Toast.makeText(context, "密码中不能包含空格", Toast.LENGTH_SHORT).show()
            } else if (!str.matches("(.*\\D.*){1,8}|.{9,}".toRegex())) {
                Toast.makeText(context, "不能是9位以下的纯数字", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    /**
     * 验证身份证号码
     * @param num
     * @return
     */
    fun validateIdCard(num: String): Boolean {
        val reg = "^\\d{15}$|^\\d{17}[0-9Xx]$"
        return num.matches(reg.toRegex())
    }

    /**
     * 验证用户名称
     * @param name
     * @return
     */
    fun validateNickname(name: String): Boolean {
        val reg = "[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*"
        return if (TextUtils.isEmpty(name)) {
            false
        } else Pattern.matches(reg, name)
    }

    /**
     * @param price 非String类型的数据,可以通过重载 拓展校验的方法
     * @return
     */
    fun validatePrice(price: String): Boolean {
        val regex = "(^[1-9]\\d*(\\.\\d{1,2})?$)|(^0(\\.\\d{1,2})?$)"
        //将给定的正则表达式编译到模式中。
        val pattern = Pattern.compile(regex)
        //创建匹配给定输入与此模式的匹配器。
        val isNum = pattern.matcher(price)
        //如果匹配成功，则可以通过 start、end 和 group 方法获取更多信息.
        return isNum.matches()
    }

}