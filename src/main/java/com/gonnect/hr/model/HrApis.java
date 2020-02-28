package com.gonnect.hr.model;

import net.razorvine.pyro.NameServerProxy;
import net.razorvine.pyro.PyroProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController

public class HrApis {

    private final PyroProxy remoteobject;

    public HrApis() throws IOException {
        NameServerProxy ns = NameServerProxy.locateNS(null);
        remoteobject = new PyroProxy(ns.lookup("NabeeModel"));
    }


    @GetMapping("/hr/predict")
    public ModelPredict predict(@RequestParam double level) throws IOException {
        List<Double> output = (List<Double>) remoteobject.call("predict",new Double[] {level});

        return new ModelPredict(output.get(0));
    }
}
