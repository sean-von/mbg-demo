package com.smikevon.easy.common.utils;

import static java.util.stream.Collectors.toList;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

import org.jooq.lambda.Unchecked;

/**
 * Created by sean (smikevon@163.com) on 2018/8/22.
 */
public class IPUtils {

    private static List<NetworkInterface> getInterfaces() throws SocketException {
        return Collections.list(NetworkInterface.getNetworkInterfaces());
    }

    private static List<NetworkInterface> getNonLoopBackInterfaces() throws SocketException {
        return getInterfaces().stream()
                .filter(i -> Unchecked.supplier(() -> !i.isLoopback()).get())
                .collect(toList());
    }

    public static List<String> getIp4Addresses() throws SocketException {
        final List<NetworkInterface> is = getNonLoopBackInterfaces();
        return is.stream().flatMap(i -> {
            final Enumeration<InetAddress> addresses = i.getInetAddresses();
            final Stream.Builder<String> builder = Stream.builder();
            while (addresses.hasMoreElements()) {
                final InetAddress ip = addresses.nextElement();
                if (!ip.isLoopbackAddress()) {
                    if (ip.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                        continue;
                    }
                    if (ip instanceof Inet6Address) {
                        continue;
                    }
                    if (ip instanceof Inet4Address) {
                        builder.add(ip.getHostAddress());
                    }
                }
            }
            return builder.build();
        }).collect(toList());
    }

    public static String getIp4Address() {
        try {
            for (String ip : getIp4Addresses()) {
                if (ip.startsWith("172")) {
                    continue;
                }
                return ip;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        throw new NullPointerException();
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(Arrays.toString(getIp4Addresses()));
        System.out.println(getIp4Address());
    }
}
