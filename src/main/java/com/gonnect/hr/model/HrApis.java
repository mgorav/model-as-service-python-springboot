package com.gonnect.hr.model;

import net.razorvine.pyro.NameServerProxy;
import net.razorvine.pyro.PyroProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class HrApis {

    private final PyroProxy remoteObject;

    public HrApis() throws IOException {
        // automatically locate running Pyro server which contains pickled python object
        NameServerProxy ns = NameServerProxy.locateNS(null);
        // Get the python object in Java
        remoteObject = new PyroProxy(ns.lookup("NabeeModel"));
    }


    @GetMapping(value = "/hr/predict", produces = APPLICATION_JSON_VALUE)
    public ModelPredict predict(@RequestParam double level) throws IOException {
        List<Double> output = (List<Double>) remoteObject.call("predict", new Double[]{level});

        return new ModelPredict(output.get(0));
    }

    @GetMapping(value = "/hr/predictor", produces = APPLICATION_JSON_VALUE)
    public PyroProxy predictor()  {


        return remoteObject;
    }

    @GetMapping(value = "/hr/features", produces = APPLICATION_JSON_VALUE)
    public List<String> features() throws IOException {

        return (List<String>) remoteObject.call("get_features");

    }
}
