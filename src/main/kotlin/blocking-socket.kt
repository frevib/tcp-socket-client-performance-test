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

    val connections = 4
    val requests = 500_000
    val testIterations = 5

    val time = measureTimeMillis {

        for (t in (1..testIterations)) {

            runBlocking {
                for (i in (1..connections)) {

                    launch(Dispatchers.Default) {
                        val client = Socket()
                        client.connect(InetSocketAddress("127.0.0.1", 1815), 10000)

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
            time spent: $time
            amount of socket writes: ${connections * requests}
            req/s: ${(connections * requests) / (time.toFloat() / testIterations) * 1000}
            """.trimIndent())

}