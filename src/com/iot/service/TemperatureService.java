package com.iot.service;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.iot.dto.TemperatureLimits;
import com.iot.util.SensorUtils;

import mraa.Aio;

@Path("/temperature")
public class TemperatureService {
    private static final String FILE_NAME = "alarm.conf";

    @GET
    @Path("/read")
    @Produces("application/json")
    public Double getTemperature() {
        Aio sensor = new Aio(5);
        return SensorUtils.convertToCelsius(sensor.read());
    }

    @GET
    @Path("/limits")
    @Produces("application/json")
    public TemperatureLimits getLimits() throws IOException {
        TemperatureLimits limits;

        ObjectMapper mapper = new ObjectMapper();
        limits = mapper.readValue(new File(FILE_NAME), TemperatureLimits.class);

        return limits;
    }

    @GET
    @Path("/setlimits")
    @Produces("application/json")
    public Response setLimits(@QueryParam("min") Double min, @QueryParam("max") Double max) throws IOException {
        TemperatureLimits limits = new TemperatureLimits(min, max);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ow.writeValue(new File(FILE_NAME), limits);

        return Response.status(200).build();
    }
}
