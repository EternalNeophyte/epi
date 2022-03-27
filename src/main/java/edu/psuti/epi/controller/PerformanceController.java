package edu.psuti.epi.controller;

import edu.psuti.epi.dto.CpuDescription;
import edu.psuti.epi.dto.Evaluation;
import edu.psuti.epi.math.PerformanceMath;
import org.springframework.web.bind.annotation.*;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

@RestController
@RequestMapping
public class PerformanceController {

    @GetMapping("/evaluate")
    public Evaluation evaluate(@RequestParam("size") int size,
                               @RequestParam("runs") int runs,
                               @RequestParam(value = "precision",
                                            required = false,
                                            defaultValue = "6") int precision) {
        return PerformanceMath.evaluate(size, runs, precision);
    }

    @GetMapping("/cpu")
    public CpuDescription findOut() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        CentralProcessor.ProcessorIdentifier processorId = processor.getProcessorIdentifier();
        return new CpuDescription(
                processorId.getName(),
                processor.getPhysicalPackageCount(),
                processor.getPhysicalProcessorCount(),
                processor.getLogicalProcessorCount(),
                processorId.getMicroarchitecture(),
                processorId.getVendorFreq(),
                processorId.getVendorFreq() / 1000000000.0
        );
    }
}
