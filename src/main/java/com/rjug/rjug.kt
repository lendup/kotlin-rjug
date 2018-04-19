package com.rjug

import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.concurrent.thread

/**
 * functions
 *  - function declarations
 *  - named arguments
 *  - single expression functions
 *  - varargs
 *  - infix
 *  - function scope
 *      - package level functions
 *      - local functions
 *      - member functions
 *  - inline
 *  - generic functions
 *  - higher order functions and lambdas
 *  - tail recursive functions
 */
fun <T> join(collection: Collection<T> = emptyList(),
             prefix: String = "(",
             postfix: String = ")",
             separator: String = ",",
             transform: (T) -> String = { it.toString() }) : String {

    var result = StringBuilder(prefix)

    for ((index, value) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(transform(value))
    }

    result.append(postfix)
    return result.toString();
}

private fun <E> Collection<E>.customJoin(prefix: String = "(",
                                         postfix: String = ")",
                                         separator: String = ",",
                                         transform: (E) -> String = {it.toString() }) : String {

    var result = StringBuilder(prefix)

    for ((index, value) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(transform(value))
    }

    result.append(postfix)
    return result.toString();
}

tailrec fun factorial(n: BigDecimal, result: BigDecimal = 1.bd) : BigDecimal =
        when {
            n <= 0.bd -> result
            else -> factorial(n - 1.bd, result * n)
        }

val fizzBuzz = {
    number: Int ->
    when {
        number isDivisibleBy 15 -> "FizzBuzz"
        number isDivisibleBy 3 -> "Fizz"
        number isDivisibleBy 5 -> "Buzz"
        else -> number
    }
}

private infix fun Int.isDivisibleBy(i: Int) = this % i == 0

fun benchmark(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

inline fun sum(a: Int, b: Int) = a + b

private val Int.bd: BigDecimal
    get() = BigDecimal(this)


/**
 * Classes
 *  - open
 *  - data
 *  - sealed
 *  - copy - specify members to be changed
 */

/**
 * Companion objects - Singletones
 */

/**
 * Destructuring declarations
 */
data class User(val first: String, val last:String, val phone: String = "")

fun getUser() = User("Srki", "Rakic")

fun destruct() {
    val (first, last) = getUser()
    println("$last, $first")
}

/**
 * Coroutines
 */
//fun main(args: Array<String>)  {
//    println("Start")
////    coroutines()
//    threads()
//    println("Stop")
//
//}

fun coroutines() = runBlocking {
    //    val time = measureTimeMillis {
//        val sum = (1..1_000_000).map {
//            n -> async { n }
//        }.sumBy { it.await() }
//        println(sum)
//    }

//    println(time)

    List(100000) {
        launch {
            println(".")
        }
    }.forEach { it.join() }
}

fun threads() {
//    val c = AtomicInteger()
//    val time = measureTimeMillis {
//        for (i in 1..1_000_000) thread { c.addAndGet(i) }
//    }
//    println(c.get())

    val jobs = 1..100000
    jobs.forEach {
        thread {
            Thread.sleep(1000L)
            println(".")
        }
    }
}

class CoroutinesTests {
    @Test fun `run coroutines`() {
        coroutines()
    }

    @Test fun `run threads`() {
        threads()
    }
}

/**
 * Generics
 */

/**
 * Collections
 */
fun collections() {
    val list = listOf(1, 2, 3, 4)
    val mutableList = mutableListOf("a", "b")
    mapOf(Pair("1", 1))
    mapOf(1 to "1", 2 to "2")

}

/**
 * Type casts
 */
fun cast(input: Any) : String? {
    return input as? String
}

/**
 * Null safety
 */
fun nullSafety(name: String?, birthDate: LocalDate?) : String {
    return "${name ?: "anonymous"} Birth date: ${birthDate ?: LocalDate.now()}"
//    name?.let {
//
//    }
//
//    return name?.length ?: 10
}

/**
 * Annotations
 */
@Deprecated(
        "This is an old method silly",
        ReplaceWith("newMethod(name)"),
        DeprecationLevel.WARNING)
fun oldMethod(name: String) = name.length
fun newMethod(name: String) = name.length

/**
 * lazy init from "kotlin deep dive"
 * Initialize property when first time referenced.
 */

class Toolbar(var content: String)
class View() {
    val toolbar : Toolbar by lazy {
        println("Initializing toolbar")
        Toolbar("File View")
    }

    fun content() : String {
        return toolbar.content
    }
}

/**
 * Preconditions / input validations
 * require - Illegal argument exception
 * check - illegal state exception
 */
fun calculate(number: Int?) : Int? {
    require(number in (1..100))
    check(number == null) { "Illegal state!" }
    return number?.let { number + 10 }
}

/**
 * spread operator
 */
fun <T> printArgs(vararg args: T) {
    for (arg in args) {
        println(arg)
    }
}

fun spread() {
    val array = arrayOf(5, 6, 7)
    printArgs(1, 2, 3, 4, *array, 8, 9)
}

/**
 * kotlin switch/when
 */
fun kotlinSwitch(input: Any) {
    when (input) {
        "1" -> "It's 1"
        "2", 5 -> "It's 2 or 5"
        'a'..'Z' -> "It's alphabet"
        (1..100) -> "It's one to 100"
        in (listOf(3, 4, 5)) -> input.toString()
        is String -> input.capitalize()
        !is String -> "Not a string"
        else -> "No match"
    }

    when {
        (input is String && input.length > 0)  -> "It's a string with length greater than 0"
    }
}

/**
 * ranges
 *
 */
fun ranges() {
    (1..10).toList();
    (1..10).forEach { println(it) }
    (1 until 10).forEach { println(it) }
    repeat(10) {
        println("Hello")
    }
//    for (number in 1..100) println(fizzBuzz(number))
    // more ranges
//    for (i in 1..100) { ... }  // closed range: includes 100
//    for (i in 1 until 100) { ... } // half-open range: does not include 100
//    for (x in 2..10 step 2) { ... }
//    for (x in 10 downTo 1) { ... }
//    if (x in 1..10) { ... }
}

/**
 * if as an expression
 *  - returning different types
 */
fun foo(param: Int): String {
    val result = if (param == 1) {
        "one"
    } else if (param == 2) {
        "two"
    } else {
        "three"
    }
    return result
}

/**
 * try/catch as an expression
 *  - returning different types
 */
fun tryIsExpression() {
    val result = try {
        foo(1)
    } catch (e: ArithmeticException) {
        throw IllegalStateException(e)
    }
    // Working with result
    println(result)
}


/**
 * let, with, run, apply
 * Need to return self?
 *      - Use T.apply if you need to send this as argument
 *      - Use T.also if you need to send it as argument
 *
 * No need to return self?
 *   Need extension functions (e.g null checks, chaining)?
 *      - Use T.run() if you need send this as argument
 *      - Use T.let() if you need to send it as argument
 *   Else
 *      - user with() if you need to send this as argument
 *      - use run() if you don't need to send this as argument
 */
enum class LayoutType {
    HORIZONTAL,
    VERTICAL,
    GRID
}

class Layout() {
    var type =  LayoutType.VERTICAL
    var margin = 0.5
    var spacing = 0.5
    var alignment = "center"
    var size = 100;

    fun show() = println("Showing the layout")
}

fun main(args: Array<String>) {
    val layout = Layout()

    // set all properties at once
    // returns last object in the block
    with (layout) {
        type = LayoutType.HORIZONTAL
        margin = 1.0
        size = 50
    }

    // what if layout is nullable type?
    // returns last object in the block
    // it's a receiver so you can access all properties directly
    layout?.run {
        type = LayoutType.HORIZONTAL
    }

    // returns last object in the within the block
    run {

    }

    // returns itself
    layout?.apply {
        type = LayoutType.HORIZONTAL
    }.show()


    // Passes itself to let so you can reference using it.
    layout?.let {
        it.show()
    }
}

fun makeDir(path: String) = path.let { File(it) }.also { it.mkdirs() }


