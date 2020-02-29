package com.gonnect.hr.model;

import com.hazelcast.core.HazelcastInstance;
import net.razorvine.pyro.NameServerProxy;
import net.razorvine.pyro.PyroProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class HrApis {
    // Cache the remote model object locally in JVM
    private final PyroProxy remoteObject;
    @Autowired
    private HazelcastInstance hazelcastInstance;
    private Map<String, PyroProxy> hazelcastMap;
    private final String KEY = "nabee_model";

    @PostConstruct
    public void postConstrucxt() {
        hazelcastMap = hazelcastInstance.getMap("my-map");
        hazelcastMap.put(KEY, remoteObject);
    }

    public HrApis() throws IOException {
        // automatically locate running Pyro server which contains pickled python object
        NameServerProxy ns = NameServerProxy.locateNS(null);
        // Get the python object in Java
        remoteObject = new PyroProxy(ns.lookup("NabeeModel"));

    }


    @GetMapping(value = "/hr/predict", produces = APPLICATION_JSON_VALUE)
    public ModelPredict predict(@RequestParam double level) throws IOException {
        List<Double> output = (List<Double>) hazelcastMap.get(KEY).call("predict", new Double[]{level});

        return new ModelPredict(output.get(0));
    }

    @GetMapping(value = "/hr/predictor", produces = APPLICATION_JSON_VALUE)
    public PyroProxy predictor() {


        return remoteObject;
    }

    @GetMapping(value = "/hr/features", produces = APPLICATION_JSON_VALUE)
    public List<String> features() throws IOException {

        return (List<String>) hazelcastMap.get(KEY).call("get_features");

    }
}
