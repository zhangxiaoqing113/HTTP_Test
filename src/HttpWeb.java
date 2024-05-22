import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class HttpWeb {

    /**
     * 具体的看 请求报文.txt
     *
     * 请求行
     * 请求头
     * 空行
     * 请求体
     * @param args
     */
    public static void main(String[] args) {
       int port=56565;
        try {
            //生成服务端的socket
            ServerSocket serverSocket=new ServerSocket(port);
            //根据服务端的socket 生成 "欢迎"Socket
            Socket socket1 = serverSocket.accept();
            //把客户端的请求报文  读进来
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            //读取 请求行
            String requestLine = reader.readLine();
            System.out.println("请求行");
            System.out.println(requestLine);
            //读取 请求头(首部行)
            String requestHead= "";
            //请求体的长度 在请求头中 Content-Length字段里封装 Content-Length: 27
            int length=0;
            System.out.println("请求头");
            //读取到空行后 跳出循环
            while (!(requestHead= reader.readLine()).trim().isEmpty()){
                System.out.println(requestHead);
               if(requestHead.startsWith("Content-Length")){
                   length= Integer.valueOf(requestHead.split(": ")[1]);
                   System.out.println("length = "+length);
               }
            }

            //此时reader里边 没读的就是 请求体了
            //reader
           char [] body=new char[length];
            //将 请求体读进 char数组 中
            reader.read(body);
            String s=new String(body);
            System.out.println();
            System.out.println("请求体");
            System.out.println(s);

            //响应报文
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
            //响应行
            writer.write("HTTP/1.1 200 OK\r\n");
            //响应头(首部行)
            writer.write("Content-Type: text/html; charset=UTF-8\r\n");
            writer.write("Content-Length: 1000\r\n");
            writer.write("\r\n");
            //响应体
            writer.write("<!DOCTYPE html>\r\n");
            writer.write("<html lang=\"en\">\r\n");
            writer.write("<meta charset=\"UTF-8\">\r\n");
            writer.write("<title>登录成功</title>\r\n");
            writer.write("</head>\r\n");
            writer.write("<body>\r\n");
            writer.write("<h1>欢迎，用户名！</h1>\r\n");
            writer.write("<p>登录成功，您现在可以访问受保护的内容。</p>\r\n");
            writer.write("</body>\r\n");
            writer.write("</html>\r\n");
            writer.flush();
            reader.close();
            writer.close();
            socket1.close();
            serverSocket.close();

        } catch (Exception e) {
            System.out.println(e);
        }


    }

}
