package TCP;

import java.net.InetAddress;

public record TCPMessage(String content, InetAddress origin) {

}
