package com.xl.xl_base.tool.ktx

/**
 * 描述:
 *
 * @author zys
 * create by 2020/04/10
 */
/**
 * 用于is判断
 */
inline fun <reified R : Any> Any?.isClass(crossinline block: R.() -> Unit = {}): R? {
    return (if (this is R) this else null)?.apply { block(this) }
}

/**
 * Any 转 List
 */
fun Any.toList(): MutableList<Any?> =
    if (isList()) (this as MutableList<*>).toMutableList() else mutableListOf()

/**
 * 判断Any是否为List
 */
fun Any.isList() = this is List<*> || this is ArrayList<*> || this is MutableList<*>

/**
 * 判断不为空
 */
inline fun String?.notEmpty(crossinline bock: (String) -> Unit = {}): Boolean =
    if (this.isNullOrEmpty()) {
        false
    } else {
        bock(this)
        true
    }

/**
 * 判断不为空
 */
inline fun <T> List<T>?.notEmpty(crossinline bock: (List<T>) -> Unit = {}) =
    if (this.isNullOrEmpty()) {
        false
    } else {
        bock(this)
        true
    }


/**
 * 判断数字不为空，并大于某个数
 */
inline fun <T : Number> T?.moreThan(
    target: Int = 0, equals: Boolean = false,
    crossinline bock: (T) -> Unit
) {
    if (equals) {
        when {
            this is Int && this >= target -> bock(this)
            this is Long && this >= target -> bock(this)
            this is Short && this >= target -> bock(this)
            this is Byte && this >= target -> bock(this)
            this is Double && this >= target -> bock(this)
            this is Float && this >= target -> bock(this)
        }
    } else {
        when {
            this is Int && this > target -> bock(this)
            this is Long && this > target -> bock(this)
            this is Short && this > target -> bock(this)
            this is Byte && this > target -> bock(this)
            this is Double && this > target -> bock(this)
            this is Float && this > target -> bock(this)
        }
    }
}

/**
 * 判断数字不为空，并小于某个数
 */
inline fun <T : Number> T?.lessThan(
    target: Int = 0, equals: Boolean = false,
    crossinline bock: (T) -> Unit
) {
    if (equals) {
        when {
            this is Int && this <= target -> bock(this)
            this is Long && this <= target -> bock(this)
            this is Short && this <= target -> bock(this)
            this is Byte && this <= target -> bock(this)
            this is Double && this <= target -> bock(this)
            this is Float && this <= target -> bock(this)
        }
    } else {
        when {
            this is Int && this < target -> bock(this)
            this is Long && this < target -> bock(this)
            this is Short && this < target -> bock(this)
            this is Byte && this < target -> bock(this)
            this is Double && this < target -> bock(this)
            this is Float && this < target -> bock(this)
        }
    }
}

inline fun Boolean?.yes(crossinline block: () -> Unit) {
    if (this == true) {
        block.invoke()
    }
}

/**
 * A对象和B对象都不为Null
 * A用this调用，B用it调用
 */
inline fun <A, B> A?.andNoNull(b: B?, crossinline block: A.(B) -> Unit) {
    if (this != null && b != null) {
        block.invoke(this, b)
    }
}

/**
 * A对象和B，C对象都不为Null
 * A用this调用，B，C 自定义变量名
 */
inline fun <A, B, C> A?.andNoNull(
    b: B?, c: C?,
    crossinline block: A.(B, C) -> Unit
) {
    if (this != null && b != null && c != null) {
        block(this, b, c)
    }
}