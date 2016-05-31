package com.iot.service;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.iot.dto.AlarmStatus;

@Path("/alarm")
public class AlarmService {
    public static final String FILE_NAME = "alarm.status";

    @GET
    @Path("/status")
    @Produces("application/json")
    public Boolean status() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AlarmStatus alarmStatus = mapper.readValue(new File(AlarmService.FILE_NAME), AlarmStatus.class);
        return alarmStatus.isOn();
    }

    @GET
    @Path("/on")
    public Response on() throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ow.writeValue(new File(FILE_NAME), new AlarmStatus(true));
        return Response.status(200).build();
    }

    @GET
    @Path("/off")
    public Response off() throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ow.writeValue(new File(FILE_NAME), new AlarmStatus(false));
        return Response.status(200).build();
    }
}
