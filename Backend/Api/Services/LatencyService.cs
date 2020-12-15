using System;

namespace Api.Services
{
    public class LatencyService
    {
        public LatencyService(SessionService bs)
        {
            bs.RegisterRequestHandler<object>("ping", (session, payload) => session.Send("pong"));
        }
    }
}