package edu.psuti.epi.dto;

public record CpuDescription
        (String processorName,
         int physicalPackages,
         int physicalCores,
         int logicalCores,
         String microarchitecture,
         long frequencyHz,
         double frequencyGHz) { }
