/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.net.InetAddress;

/**
 *
 * @author HP
 */
public class IPAddressUtil {
    public static String getClientIP(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (Exception e) {
        }
        return null;
    }
}
