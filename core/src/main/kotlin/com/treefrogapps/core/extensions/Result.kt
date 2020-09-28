package com.treefrogapps.core.extensions

class Result<out T> private constructor(private val value: T? = null, private val throwable: Throwable? = null) {

    companion object {

        fun <T> success(t: T) = Result<T>(value = t)
        fun <T, E : Throwable> failure(e: E) = Result<T>(throwable = e)
    }

    val isSuccess: Boolean get() = value != null
    val isFailure: Boolean get() = throwable != null

    fun getOrNull(): T? = value

    fun exceptionOrNull(): Throwable? = throwable

    fun <E : Throwable> getOrThrow(e: E): T =
            when {
                isSuccess -> value!!
                else      -> throw e
            }

    override fun equals(other: Any?): Boolean =
            when {
                isSuccess -> value!! == other
                else      -> throwable!! == other
            }

    override fun hashCode(): Int =
            when {
                isSuccess -> value!!.hashCode()
                else      -> throwable!!.hashCode()
            }

    override fun toString(): String =
            when {
                isSuccess -> value!!.toString()
                else      -> throwable!!.toString()
            }
}

inline fun <T, R> Result<T>.flatMap(func: (T) -> Result<R>): Result<R> =
        when {
            isSuccess -> func(getOrNull()!!)
            else      -> Result.failure(exceptionOrNull()!!)
        }

inline fun <T, R> Result<T>.map(func: (T) -> R): Result<R> =
        when {
            isSuccess -> Result.success(func(getOrNull()!!))
            else      -> Result.failure(exceptionOrNull()!!)
        }

inline fun <T> Result<T>.orElseGet(func: () -> T): T =
        when {
            isSuccess -> getOrNull()!!
            else      -> func()
        }

fun <T> Result<T>.getOrDefault(value: T): T = getOrNull() ?: value