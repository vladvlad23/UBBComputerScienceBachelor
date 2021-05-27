using System;
using System.Net;
using System.Net.Sockets;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public class SecondImplementation
    {
        public void startWithURL(string url)
        {
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

            ConnectTask(state).Wait();
            SendTask(state, GetRequestString(state.hostname, state.requestEndpoint)).Wait();
            ReceiveTask(state).Wait();

            Console.WriteLine("Received task response from: " + state.hostname + " " + GetContentLength(state.response.ToString()) + " " + state.response.Length);
            Console.WriteLine(state.response.ToString());
            socket.Shutdown(SocketShutdown.Both);
            socket.Close();
        }

        private static Task ConnectTask(CurrentState state)
        {
            state.targetSocket.BeginConnect(state.targetEndpoint, ConnectFunction, state);
            return Task.FromResult(state.connectMutex.WaitOne());
        }

        private static void ConnectFunction(IAsyncResult asyncResult)
        {
            
            var state = (CurrentState) asyncResult.AsyncState;
            var targetSocket = state.targetSocket;
            var hostname = state.hostname;

            targetSocket.EndConnect(asyncResult);
            Console.WriteLine("Socket task connected " + hostname + targetSocket.RemoteEndPoint);

            state.connectMutex.Set();
        }

        private static Task SendTask(CurrentState state, string data)
        {
            var byteData = Encoding.ASCII.GetBytes(data);
            state.targetSocket.BeginSend(byteData, 0, byteData.Length,0, SendFunction, state);
            return Task.FromResult(state.sendMutex.WaitOne());
        }

        private static void SendFunction(IAsyncResult asyncResult)
        {
            var state = (CurrentState) asyncResult.AsyncState;
            var targetSocket = state.targetSocket;
            var hostname = state.hostname;

            var bytesSent = targetSocket.EndSend(asyncResult);
            Console.WriteLine("Sent task to server hostname " + hostname + " " + bytesSent + " bytes");
            state.sendMutex.Set();
        }

        private static Task ReceiveTask(CurrentState state)
        {
            state.targetSocket.BeginReceive(state.receivingBuffer, 0, CurrentState.BUFFER_SIZE, 0, ReceiveFunction,
                state);

            return Task.FromResult(state.receiveMutex.WaitOne());
        }
        
        public static int GetContentLength(string responseContent)
        {
            var contentLength = 0;

            var responseLines = responseContent.Split('\r', '\n');
            foreach (var responseLine in responseLines)
            {
                // name:value
                var headerDetails = responseLine.Split(':');

                if (headerDetails[0].CompareTo("Content-Length") == 0)
                {
                    contentLength = int.Parse(headerDetails[1]);
                }
            }

            return contentLength;
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

        private static void ReceiveFunction(IAsyncResult asyncResult)
        {
            var state = (CurrentState) asyncResult.AsyncState;
            var targetSocket = state.targetSocket;

            try
            {
                var bytesRead = targetSocket.EndReceive(asyncResult);
                state.response.Append(Encoding.ASCII.GetString(state.receivingBuffer, 0, bytesRead));
                
                if (state.response.Length < GetContentLength(state.response.ToString()))
                {
                    targetSocket.BeginReceive(state.receivingBuffer, 0, CurrentState.BUFFER_SIZE, 0, ReceiveFunction,
                        state);
                }
                else
                {
                    var responseParts = state.response.ToString()
                        .Split(new[] {"\r\n\r\n"}, StringSplitOptions.RemoveEmptyEntries);
                    var responseBody = responseParts.Length > 1 ? responseParts[1] : "";

                    if (responseBody.Length < GetContentLength(state.response.ToString()))
                    {
                        targetSocket.BeginReceive(state.receivingBuffer, 0, CurrentState.BUFFER_SIZE, 0,
                            ReceiveFunction, state);

                    }
                    else
                    {
                        state.receiveMutex.Set();
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }
    }
}