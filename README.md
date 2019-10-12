### TCP socket client for high load testing
This is a socket client written in Kotlin. It uses coroutines
to run socket writes in parallel.


### Use
Set the host/port and amount of connections/requests(socket writes)/test iterations
in `socket-client-blocking-io.kt`

run 
`./gradlew execute`
