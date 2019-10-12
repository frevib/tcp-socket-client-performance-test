@file:JvmName("socket-client-blocking-io.kt")

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.system.measureTimeMillis


fun main() {

    val host = "127.0.0.1"
    val port = 1815

    val connections = 3
    val requests = 500_000
    val testIterations = 3

    val time = measureTimeMillis {

        for (t in (1..testIterations)) {

            runBlocking {
                for (i in (1..connections)) {

                    launch(Dispatchers.Default) {
                        val client = Socket()
                        client.connect(InetSocketAddress(host, port), 10000)

                        val input = BufferedReader(InputStreamReader(client.getInputStream()))
                        val out = PrintWriter(client.getOutputStream(), true)

                        for (j in 1..requests) {
                            out.println("echo echo!")
                            input.readLine()
                        }

                        client.close()
                    }
                }
            }
        }
    }


    println("""
            Total running time: $time
            Requests handled: ${connections * requests}
            req/s: ${(connections * requests) / (time.toFloat() / testIterations) * 1000}
            """.trimIndent())

}