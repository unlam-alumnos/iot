package com.iot;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import mraa.Gpio;

@Path("/led")
public class LedService {
    @GET
    @Path("/{pin}")
    public Response blinkLed(@PathParam("pin") String pinNumber) {
        
        StringBuffer out = new StringBuffer();
        out.append("<html>");
        out.append("<head>");
        out.append("<title>Hello Galileo Demo!</title>");
        out.append("</head>");
        out.append("<body>");
        out.append("<h1>Hello Galileo Demo!</h1>");
        out.append("<br/>");
        out.append("<br/>");
        out.append("<p>Starting... :)</p>");

        try {
            out.append("<p>Creating GPIO(" + pinNumber + ")...</p>");
            Gpio pin = new Gpio(Integer.valueOf(pinNumber));

            out.append("<p>Turning ON the led...</p>");
            pin.write(1);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.err.append("Sleep interrupted: " + e.toString());
            }
            out.append("<p>Turning OFF the led...</p>");
            pin.write(0);

        } catch (Exception e) {
            out.append("<p>Exception: " + e.getLocalizedMessage() + " </p>");
        }

        out.append("<p>The end!</p>");
        out.append("</body>");
        out.append("</html>");

        return Response.status(200).entity(out.toString()).build();
    }
}
