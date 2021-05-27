using System;
using System.Net;
using System.Net.Sockets;
using System.Net.WebSockets;
using System.Reflection;

using System.Text;
using System.Threading;

namespace ConsoleApplication1
{
    public class FirstImplementation
    {
        public void startWithURL(string url)
        {
            //No need for the stuff after /
            var hostname = url.Split('/')[0];
            var ipHost = Dns.GetHostEntry(hostname);
            var ipAddr = ipHost.AddressList[0];
            var targetEndpoint = new IPEndPoint(ipAddr, 80);
            var socket = new Socket(ipAddr.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            var state = new CurrentState
            {
                targetSocket = socket,
                hostname = hostname,
                requestEndpoint = url.Contains("/") ? url.Substring(url.IndexOf("/", StringComparison.Ordinal)) : "/",
                targetEndpoint = targetEndpoint
            };

            state.targetSocket.BeginConnect(state.targetEndpoint, ConnectMethod, state);
        }
        
        private static void ConnectMethod(IAsyncResult asyncResult)
        {
            var state = (CurrentState) asyncResult.AsyncState;
            var targetSocket = state.targetSocket;
            var hostname = state.hostname;

            targetSocket.EndConnect(asyncResult);
            Console.WriteLine("Socket direct callback connected " + hostname + targetSocket.RemoteEndPoint);
            var data = Encoding.ASCII.GetBytes(GetRequestString(hostname, state.requestEndpoint));

            state.targetSocket.BeginSend(data, 0, data.Length, 0, SentMethod, state);
        }

        private static void SentMethod(IAsyncResult ar)
        {
            var state = (CurrentState) ar.AsyncState;
            var targetSocket = state.targetSocket;
            var hostname = state.hostname;

            var sentBytes = targetSocket.EndSend(ar);
            Console.WriteLine("Direct callback for " + hostname + "Sent " + sentBytes + " to server");

            state.targetSocket.BeginReceive(state.receivingBuffer, 0, CurrentState.BUFFER_SIZE, 0, ReceiveMethod,
                state);
        }

        private static void ReceiveMethod(IAsyncResult ar)
        {
            var state = (CurrentState) ar.AsyncState;
            var targetSocket = state.targetSocket;
            try
            {
                var bytesRead = targetSocket.EndReceive(ar);
                state.response.Append(Encoding.ASCII.GetString(state.receivingBuffer, 0, bytesRead));

                if (!state.response.ToString().Contains("\r\n\r\n")) //not fully received
                {
                    targetSocket.BeginReceive(state.receivingBuffer, 0, CurrentState.BUFFER_SIZE, 0, ReceiveMethod,
                        state);
                }
                else
                {
                    var responseParts = state.response.ToString()
                        .Split(new[] {"\r\n\r\n"}, StringSplitOptions.RemoveEmptyEntries);
                    var responseBody = responseParts.Length > 1 ? responseParts[1] : ""; //just redirect or actual content
                    var contentLengthHeaderValue = GetContentLength(state.response.ToString());
                    if (responseBody.Length < contentLengthHeaderValue) //check if received all data
                    {
                        targetSocket.BeginReceive(state.receivingBuffer, 0, CurrentState.BUFFER_SIZE, 0,
                            ReceiveMethod, state);
                    }
                    else
                    {
                        foreach (var i in state.response.ToString().Split('\r', '\n'))
                            Console.WriteLine(i);
                        Console.WriteLine("Response direct callback from " + state.hostname  + " Got " + state.response.Length);

                        targetSocket.Shutdown(SocketShutdown.Both);
                        targetSocket.Close();
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        public static string GetRequestString(string hostname, string endpoint)
        {
            return "GET " + endpoint + " HTTP/1.1\r\n" +
                   "Host: " + hostname + "\r\n" +
                   "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36\r\n"
                   +
                   "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,#1#*;q=0.8\r\n" +
                   "Accept-Language: en-US,en;q=0.9,ro;q=0.8\r\n" +
                   // the server will add the content-length header ONLY if the data comes archived (gzip)
                   "Accept-Encoding: gzip, deflate\r\n" +
                   "Connection: keep-alive\r\n" +
                   "Upgrade-Insecure-Requests: 1\r\n" +
                   "Pragma: no-cache\r\n" +
                   "Cache-Control: no-cache\r\n" +
                   "Content-Length: 0\r\n\r\n";
        }

        public static int GetContentLength(string responseContent)
        {
            var contentLength = 0;

            var responseLines = responseContent.Split('\r', '\n');
            foreach (var responseLine in responseLines)
            {
                var headerDetails = responseLine.Split(':');

                if (headerDetails[0].CompareTo("Content-Length") == 0)
                {
                    contentLength = int.Parse(headerDetails[1]);
                }
            }

            return contentLength;
        }
    }

    internal class CurrentState
    {
        internal Socket targetSocket = null;
        internal const int BUFFER_SIZE = 512;
        internal byte[] receivingBuffer = new byte[BUFFER_SIZE];
        internal StringBuilder response = new StringBuilder();
        internal string hostname;
        internal string requestEndpoint;
        internal IPEndPoint targetEndpoint;
        internal ManualResetEvent connectMutex = new ManualResetEvent(false);
        internal ManualResetEvent sendMutex = new ManualResetEvent(false);
        internal ManualResetEvent receiveMutex = new ManualResetEvent(false);
    }
}