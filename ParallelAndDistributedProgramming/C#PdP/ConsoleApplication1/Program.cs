using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Threading;

namespace ConsoleApplication1
{
    internal class Program
    {
        private static List<string> sites = new List<string>
        {
            "www.google.com",
            "www.facebook.com",
            "www.twitter.com"
        };
        public static void Main(string[] args)
        {
             // TestFirstImplementation();
            // TestSecondImplementation();
            TestThirdImplementation();
        }

        private static void TestThirdImplementation()
        {
            ThirdImplementation implementation = new ThirdImplementation();
            for (int i = 0; i < sites.Count; i++)
            {
                implementation.startWithURL(sites[i]);
            }
            Thread.Sleep(5000);
        }

        private static void TestSecondImplementation()
        {
            SecondImplementation implementation =  new SecondImplementation();
            for (int i = 0; i < sites.Count; i++)
            {
                implementation.startWithURL(sites[i]);
            }
            Thread.Sleep(5000);
        }

        private static void TestFirstImplementation()
        {
            FirstImplementation implementation = new FirstImplementation();
            for (int i = 0; i < sites.Count; i++)
            {
                implementation.startWithURL(sites[i]);
            }
            Thread.Sleep(5000);
        }
    }
}