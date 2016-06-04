package com.izipoker.network;

import java.io.IOException;
import java.io.StringReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Class with util methods related with network configuration
 */
public class NetworkUtils {

    /**
     * Function from report example project available in moodle webpage
     *
     * Parses the IP address in the URL of the client's host.
     * @param url The URL of the client's host.
     * @return Returns a textual representation of the server's IP address.
     */
    public static String parseHostName(String url) {
        StringReader sReader = new StringReader(url);
        StringBuilder sBuilder = new StringBuilder();
        boolean foundNumeric = false;
        char next;
        try {
            while ((next = (char)sReader.read()) != -1) {
                if (foundNumeric) {
                    if (next == ':')
                        return sBuilder.toString();
                    else sBuilder.append(next);
                }
                else if (Character.isDigit(next)) {
                    foundNumeric = true;
                    sBuilder.append(next);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Function from report example project available in moodle webpage
     *
     * Returns the network address of a valid network interface.
     * @return Returns the network address of a valid network interface.
     * @throws IOException An exception could be thrown when establishing socket communication.
     */
    public static InetAddress getNetworkAddress() throws IOException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface interf: Collections.list(interfaces)) {
            if (interf.isLoopback() || !interf.isUp())
                continue;

            Enumeration<InetAddress> addresses = interf.getInetAddresses();
            for (InetAddress address: Collections.list(addresses)) {
                if (address instanceof Inet6Address || !address.isReachable(3000))
                    continue;

                SocketChannel socket = null;
                try {
                    socket = SocketChannel.open();
                    socket.socket().setSoTimeout(3000);

                    socket.connect(new InetSocketAddress("google.com", 80));
                }
                catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                finally {
                    socket.close();
                }

                return address;
            }
        }

        return null;
    }
}
