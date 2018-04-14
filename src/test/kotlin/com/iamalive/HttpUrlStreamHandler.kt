package com.iamalive

import java.io.IOException
import java.net.URL
import java.net.URLConnection
import java.net.URLStreamHandler
import java.util.HashMap

class HttpUrlStreamHandler : URLStreamHandler() {

    private var connections: MutableMap<URL, URLConnection> = HashMap()

    @Throws(IOException::class)
    override fun openConnection(url: URL): URLConnection? {
        return connections[url]
    }

    fun resetConnections() {
        connections = HashMap()
    }

    fun addConnection(url: URL, urlConnection: URLConnection): HttpUrlStreamHandler {
        connections[url] = urlConnection
        return this
    }
}
