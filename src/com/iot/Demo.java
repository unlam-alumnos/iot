package com.iot;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mraa.Gpio;

public class Demo extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello Galileo Demo!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Hello Galileo Demo!</h1>");
		out.println("<br/>");
		out.println("<br/>");
		out.println("<p>Starting... :)</p>");

		try {

			out.println("<p>Creating GPIO(13)...</p>");
			Gpio pin = new Gpio(13);
			// Gpio pin = new Gpio(3);

			out.println("<p>Turning ON the led...</p>");
			pin.write(1);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.err.println("Sleep interrupted: " + e.toString());
			}
			out.println("<p>Turning OFF the led...</p>");
			pin.write(0);

		} catch (Exception e) {
			out.println("<p>Exception: " + e.getLocalizedMessage() + " </p>");
		}
		out.println("<p>The end!</p>");
		out.println("</body>");
		out.println("</html>");
	}
}